package com.apeun.gidaechi.chatdetail.repository

import com.apeun.gidaechi.chatdetail.ChatDetailRepository
import com.apeun.gidaechi.chatdetail.mapper.toModel
import com.apeun.gidaechi.chatdetail.mapper.toRoomStatus
import com.apeun.gidaechi.chatdetail.mapper.toRoomType
import com.apeun.gidaechi.chatdetail.model.ChatDetailTypeModel
import com.apeun.gidaechi.chatdetail.model.message.ChatDetailMessageLoadModel
import com.apeun.gidaechi.chatdetail.model.room.ChatRoomModel
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
import com.apeun.gidaechi.network.core.response.safeResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

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
        delay(200)
        return datasource.subscribeRoom(chatRoomId)
            .flowOn(dispatcher)
            .map {
                it.toModel()
            }
            .asResult()
    }

    override suspend fun getMessage(
        chatRoomId: Int,
        page: Int,
        size: Int
    ): Flow<Result<ChatDetailMessageLoadModel>> {
        return flow {
            val e = datasource.getMessage(chatRoomId, page, size)

            emit(e.data.toModel())
        }
            .flowOn(dispatcher)
            .asResult()
    }

    override suspend fun loadRoomInfo(
        isPersonal: Boolean,
        roomId: Int,
    ): Flow<Result<ChatRoomModel>> {
        return flow {
            val roomResponse = datasource.loadRoomInfo(isPersonal, roomId).safeResponse()
            val memberResponse = datasource.loadRoomMember(roomId).safeResponse()
            emit(
                ChatRoomModel(
                    id = roomResponse.id,
                    type = roomResponse.type.toRoomType(),
                    chatName = roomResponse.chatName,
                    containUserCnt = roomResponse.containUserCnt,
                    chatRoomImg = roomResponse.chatRoomImg,
                    createdAt = roomResponse.createdAt,
                    memberList = memberResponse.joinUserId,
                    workspaceId = memberResponse.workspaceId,
                    chatStatusEnum = roomResponse.chatStatusEnum.toRoomStatus()
                )
            )
        }
            .flowOn(dispatcher)
            .asResult()
    }

    companion object {
        const val TEST_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MiwiZW1haWwiOiJhZG1pbkBhZG1pbi5jb20iLCJyb2xlIjoiUk9MRV9BRE1JTiIsImlhdCI6MTcxNTg1ODkwMywiZXhwIjoxNzIxODU4OTAzfQ.F5_W4wAay4FbssM6XxJSCiUIvGCAcjAXqPxb-PXvUDo"
    }

}