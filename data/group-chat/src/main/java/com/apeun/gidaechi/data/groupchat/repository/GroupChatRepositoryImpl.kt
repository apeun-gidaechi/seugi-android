package com.apeun.gidaechi.data.groupchat.repository

import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.common.model.asResult
import com.apeun.gidaechi.common.utiles.DispatcherType
import com.apeun.gidaechi.common.utiles.SeugiDispatcher
import com.apeun.gidaechi.data.core.mapper.toModels
import com.apeun.gidaechi.data.core.model.ChatRoomModel
import com.apeun.gidaechi.data.groupchat.GroupChatRepository
import com.apeun.gidaechi.network.core.response.safeResponse
import com.apeun.gidaechi.network.groupchat.GroupChatDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GroupChatRepositoryImpl @Inject constructor(
    private val dataSource: GroupChatDataSource,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
): GroupChatRepository {
    override suspend fun getGroupRoomList(workspaceId: String): Flow<Result<List<ChatRoomModel>>> = flow {
        val response = dataSource.getGroupRoomList(workspaceId).safeResponse()

        emit(response.toModels())
    }
        .flowOn(dispatcher)
        .asResult()
}