package com.seugi.data.groupchat.repository

import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.core.mapper.toModel
import com.seugi.data.core.mapper.toModels
import com.seugi.data.core.model.ChatRoomModel
import com.seugi.data.groupchat.GroupChatRepository
import com.seugi.network.core.response.safeResponse
import com.seugi.network.groupchat.GroupChatDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GroupChatRepositoryImpl @Inject constructor(
    private val dataSource: GroupChatDataSource,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : GroupChatRepository {
    override suspend fun getGroupRoomList(workspaceId: String): Flow<Result<List<ChatRoomModel>>> = flow {
        val response = dataSource.getGroupRoomList(workspaceId).safeResponse()

        emit(response.toModels())
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun createChat(workspaceId: String, roomName: String, joinUsers: List<Int>, chatRoomImg: String): Flow<Result<String>> = flow {
        val response = dataSource.createChat(
            workspaceId = workspaceId,
            roomName = roomName,
            joinUsers = joinUsers,
            chatRoomImg = chatRoomImg,
        ).safeResponse()

        emit(response)
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun getGroupRoom(roomId: String): Flow<Result<ChatRoomModel>> = flow {
        val response = dataSource.getChat(
            roomId = roomId,
        ).safeResponse()

        emit(response.toModel())
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun leftRoom(chatRoomId: String): Flow<Result<Unit>> {
        return flow {
            val response = dataSource.leftRoom(chatRoomId).safeResponse()
            emit(response ?: Unit)
        }
            .flowOn(dispatcher)
            .asResult()
    }
}
