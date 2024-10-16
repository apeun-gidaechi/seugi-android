package com.seugi.data.message.repository

import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.message.MessageRepository
import com.seugi.data.message.mapper.toEventModel
import com.seugi.data.message.mapper.toModel
import com.seugi.data.message.model.MessageLoadModel
import com.seugi.data.message.model.MessageRoomEvent
import com.seugi.data.message.model.MessageType
import com.seugi.data.message.model.stomp.MessageStompLifecycleModel
import com.seugi.local.room.dao.TokenDao
import com.seugi.network.message.MessageDataSource
import javax.inject.Inject
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
    override suspend fun sendMessage(chatRoomId: String, message: String, messageUUID: String, type: MessageType): Result<Boolean> {
        return Result.Success(
            datasource.sendMessage(
                chatRoomId = chatRoomId,
                message = message,
                messageUUID = messageUUID,
                type = type.name,
            ),
        )
    }

    override suspend fun subscribeRoom(chatRoomId: String, userId: Int): Flow<Result<MessageRoomEvent>> {
        if (!datasource.getIsConnect()) {
            val token = tokenDao.getToken()
            datasource.connectStomp(
                token?.token ?: "",
            )
        }
        return datasource.subscribeRoom(chatRoomId)
            .flowOn(dispatcher)
            .map {
                it.toEventModel(userId)
            }
            .asResult()
    }

    override suspend fun reSubscribeRoom(chatRoomId: String, userId: Int): Flow<Result<MessageRoomEvent>> {
        val token = tokenDao.getToken()
        datasource.reConnectStomp(
            token?.token ?: "",
            token?.refreshToken ?: "",
        )
        delay(200)
        return datasource.subscribeRoom(chatRoomId)
            .flowOn(dispatcher)
            .map {
                it.toEventModel(userId)
            }
            .asResult()
    }

    override suspend fun getMessage(chatRoomId: String, timestamp: LocalDateTime?, userId: Int): Flow<Result<MessageLoadModel>> {
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
}
