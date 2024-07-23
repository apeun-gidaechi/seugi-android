package com.seugi.data.personalchat.repository

import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.core.mapper.toModels
import com.seugi.data.core.model.ChatRoomModel
import com.seugi.data.personalchat.PersonalChatDataSource
import com.seugi.data.personalchat.PersonalChatRepository
import com.seugi.network.core.response.safeResponse
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PersonalChatRepositoryImpl @Inject constructor(
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
    private val dataSource: PersonalChatDataSource,
) : PersonalChatRepository {
    override suspend fun getAllChat(workspaceId: String): Flow<Result<List<ChatRoomModel>>> = flow {
        val response = dataSource.getAllChat(workspaceId).safeResponse()
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
}
