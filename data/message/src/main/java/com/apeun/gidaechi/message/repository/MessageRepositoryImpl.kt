package com.apeun.gidaechi.message.repository

import com.apeun.gidaechi.message.MessageRepository
import com.apeun.gidaechi.message.mapper.toModel
import com.apeun.gidaechi.message.mapper.toMessageRoomStatus
import com.apeun.gidaechi.message.mapper.toMessageRoomType
import com.apeun.gidaechi.message.model.MessageTypeModel
import com.apeun.gidaechi.message.model.message.MessageLoadModel
import com.apeun.gidaechi.message.model.room.MessageRoomModel
import com.apeun.gidaechi.common.model.asResult
import com.apeun.gidaechi.common.utiles.DispatcherType
import com.apeun.gidaechi.common.utiles.SeugiDispatcher
import com.apeun.gidaechi.network.message.MessageDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.message.model.message.MessageUserModel
import com.apeun.gidaechi.network.core.response.safeResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class MessageRepositoryImpl @Inject constructor(
    private val datasource: MessageDataSource,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher
): MessageRepository {
    override suspend fun sendMessage(chatRoomId: String, message: String): Result<Boolean> {
        return Result.Success(
            datasource.sendMessage(
                chatRoomId = chatRoomId,
                message = message
            )
        )
    }

    override suspend fun subscribeRoom(chatRoomId: String): Flow<Result<MessageTypeModel>> {
        if (!datasource.getIsConnect()) {
            datasource.connectStomp(TEST_TOKEN)
        }
        return datasource.subscribeRoom(chatRoomId)
            .flowOn(dispatcher)
            .map {
                it.toModel()
            }
            .asResult()
    }

    override suspend fun reSubscribeRoom(chatRoomId: String): Flow<Result<MessageTypeModel>> {
        datasource.reConnectStomp(TEST_TOKEN)
        delay(200)
        return datasource.subscribeRoom(chatRoomId)
            .flowOn(dispatcher)
            .map {
                it.toModel()
            }
            .asResult()
    }

    override suspend fun getMessage(
        chatRoomId: String,
        page: Int,
        size: Int
    ): Flow<Result<MessageLoadModel>> {
        return flow {
            val e = datasource.getMessage(chatRoomId, page, size)

            emit(e.data.toModel())
        }
            .flowOn(dispatcher)
            .asResult()
    }

    override suspend fun loadRoomInfo(
        isPersonal: Boolean,
        roomId: String,
    ): Flow<Result<MessageRoomModel>> {
        return flow {
            val roomResponse = datasource.loadRoomInfo(isPersonal, roomId).safeResponse()
            emit(roomResponse.toModel())
        }
            .flowOn(dispatcher)
            .asResult()
    }

    override suspend fun leftRoom(chatRoomId: String): Flow<Result<Unit>> {
        return flow {
            val response = datasource.leftRoom(chatRoomId).safeResponse()
            emit(response?: Unit)
        }
            .flowOn(dispatcher)
            .asResult()
    }

    companion object {
        const val TEST_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MiwiZW1haWwiOiJhZG1pbkBhZG1pbi5jb20iLCJyb2xlIjoiUk9MRV9BRE1JTiIsImlhdCI6MTcxNTg1ODkwMywiZXhwIjoxNzIxODU4OTAzfQ.F5_W4wAay4FbssM6XxJSCiUIvGCAcjAXqPxb-PXvUDo"
    }

}