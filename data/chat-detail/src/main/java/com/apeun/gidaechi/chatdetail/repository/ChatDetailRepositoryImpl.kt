package com.apeun.gidaechi.chatdetail.repository

import com.apeun.gidaechi.chatdetail.ChatDetailRepository
import com.apeun.gidaechi.chatdetail.mapper.toModel
import com.apeun.gidaechi.chatdetail.model.ChatDetailTypeModel
import com.apeun.gidaechi.common.model.asResult
import com.apeun.gidaechi.common.utiles.DispatcherType
import com.apeun.gidaechi.common.utiles.SeugiDispatcher
import com.apeun.gidaechi.network.chatdetail.ChatDetailDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.apeun.gidaechi.common.model.Result

class ChatDetailRepositoryImpl @Inject constructor(
    private val datasource: ChatDetailDataSource,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher
): ChatDetailRepository {
    override suspend fun sendMessage(chatRoomId: Int, message: String): Result<Boolean> {
        return Result.Success(
            datasource.sendMessage(
                chatRoomId = chatRoomId,
                message = message
            )
        )
    }

    override suspend fun subscribeRoom(chatRoomId: Int): Flow<Result<ChatDetailTypeModel>> {
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

    override suspend fun reSubscribeRoom(chatRoomId: Int): Flow<Result<ChatDetailTypeModel>> {
        datasource.reConnectStomp(TEST_TOKEN)

        return datasource.subscribeRoom(chatRoomId)
            .flowOn(dispatcher)
            .map {
                it.toModel()
            }
            .asResult()
    }

    companion object {
        const val TEST_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MywiZW1haWwiOiJ0ZXN0QHRlc3QiLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzE1Nzc3MjY5LCJleHAiOjE3MTU3ODMyNjl9.8ERrrbpfgvFj2v48_ihkIokwo7cfvl_y6Bf5hJlyDDc"
    }

}