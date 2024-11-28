package com.seugi.data.message.repository

import android.util.Log
import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.core.mapper.toModels
import com.seugi.data.core.model.TimetableModel
import com.seugi.data.message.MessageRepository
import com.seugi.data.message.mapper.toEventModel
import com.seugi.data.message.mapper.toModel
import com.seugi.data.message.model.CatSeugiResponse
import com.seugi.data.message.model.MessageBotRawKeyword
import com.seugi.data.message.model.MessageBotRawKeywordInData
import com.seugi.data.message.model.MessageLoadModel
import com.seugi.data.message.model.MessageRoomEvent
import com.seugi.data.message.model.MessageStompErrorModel
import com.seugi.data.message.model.MessageType
import com.seugi.data.message.model.stomp.MessageStompLifecycleModel
import com.seugi.local.room.dao.TokenDao
import com.seugi.network.core.response.safeResponse
import com.seugi.network.core.utiles.toResponse
import com.seugi.network.meal.response.MealResponse
import com.seugi.network.message.MessageDataSource
import com.seugi.network.notification.response.NotificationResponse
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

class MessageRepositoryImpl @Inject constructor(
    private val datasource: MessageDataSource,
    private val tokenDao: TokenDao,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : MessageRepository {
    override suspend fun sendMessage(chatRoomId: String, message: String, messageUUID: String, type: MessageType, mention: List<Long>): Result<Boolean> {
        return Result.Success(
            datasource.sendMessage(
                chatRoomId = chatRoomId,
                message = message,
                messageUUID = messageUUID,
                type = type.name,
                mention = mention,
            ),
        )
    }

    override suspend fun subscribeRoom(chatRoomId: String, userId: Long): Flow<Result<MessageRoomEvent>> {
        if (!datasource.getIsConnect()) {
            val token = tokenDao.getToken()
            datasource.connectStompSocket(
                token?.token ?: "",
            )
        }
        return datasource.subscribeRoom(chatRoomId)
            .flowOn(dispatcher)
            .map {/**/
                it.toEventModel(userId)
            }
            .asResult()
    }

    override suspend fun subscribeError(): Flow<Result<MessageStompErrorModel>> {
        if (!datasource.getIsConnect()) {
            val token = tokenDao.getToken()
            datasource.connectStompSocket(
                token?.token ?: ""
            )
        }
        return datasource.subscribeError()
            .flowOn(dispatcher)
            .map {
                it.toModel()
            }
            .asResult()
    }

    override suspend fun closeSocket(): Boolean {
        datasource.closeStompSocket()
        return true
    }

    override suspend fun reSubscribeRoom(chatRoomId: String, userId: Long): Flow<Result<MessageRoomEvent>> {
        val token = tokenDao.getToken()
        datasource.reConnectStompSocket(
            token?.token ?: "",
            token?.refreshToken ?: "",
        )
        delay(200)
        return datasource.subscribeRoom(chatRoomId)
//            .flowOn(dispatcher)
            .map {
                it.toEventModel(userId)
            }
            .asResult()
    }

    override suspend fun getMessage(chatRoomId: String, timestamp: LocalDateTime?, userId: Long): Flow<Result<MessageLoadModel>> {
        return flow<MessageLoadModel> {
            val e = datasource.getMessage(chatRoomId, timestamp)

            emit(e.data.toModel(userId))
        }
            .flowOn(dispatcher)
            .asResult()
    }

    override suspend fun collectStompLifecycle(): Flow<Result<MessageStompLifecycleModel>> = datasource.collectStompLifecycle()
        .map { it.toModel() }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun sendText(text: String): Flow<Result<String>> = flow {
        val response = datasource.sendText(text).safeResponse()

        val keyword = response.toResponse<MessageBotRawKeyword>().keyword
        Log.d("TAG", "sendText: $keyword")
        val result: CatSeugiResponse = when (keyword) {
            "급식" -> {
                val data = response.toResponse<MessageBotRawKeywordInData<List<MealResponse>>>()
                CatSeugiResponse.Meal(
                    data = data.data.toModels().toImmutableList(),
                )
            }
            "시간표" -> {
                val data = response.toResponse<MessageBotRawKeywordInData<List<TimetableModel>>>()
                CatSeugiResponse.Timetable(
                    data = data.data.toImmutableList(),
                )
            }
            "기타" -> {
                val data = response.toResponse<MessageBotRawKeywordInData<String>>()
                CatSeugiResponse.ETC(
                    data = data.data,
                )
            }
            "공지" -> {
                val data = response.toResponse<MessageBotRawKeywordInData<List<NotificationResponse>>>()
                CatSeugiResponse.Notification(
                    data = data.data.toModels().toImmutableList(),
                )
            }
            "사람 뽑기" -> {
                val data = response.toResponse<MessageBotRawKeywordInData<String>>()
                CatSeugiResponse.Picking(
                    data = data.data,
                )
            }
            "팀짜기" -> {
                val data = response.toResponse<MessageBotRawKeywordInData<String>>()
                CatSeugiResponse.Team(
                    data = data.data,
                )
            }
            else -> {
                CatSeugiResponse.NotSupport(
                    data = "현재 버전에서 지원하지 않는 메세지 유형입니다.",
                )
            }
        }

        emit(result.toModel())
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun putEmoji(messageId: String, roomId: String, emojiId: Int): Flow<Result<Boolean>> = flow {
        val response = datasource.putEmoji(
            messageId = messageId,
            roomId = roomId,
            emojiId = emojiId,
        ).safeResponse()

        emit(response)
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun deleteEmoji(messageId: String, roomId: String, emojiId: Int): Flow<Result<Boolean>> = flow {
        val response = datasource.deleteEmoji(
            messageId = messageId,
            roomId = roomId,
            emojiId = emojiId,
        ).safeResponse()

        emit(response)
    }
        .flowOn(dispatcher)
        .asResult()
}
