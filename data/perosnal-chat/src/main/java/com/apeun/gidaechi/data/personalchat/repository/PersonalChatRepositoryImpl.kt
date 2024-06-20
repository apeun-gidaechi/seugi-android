package com.apeun.gidaechi.data.personalchat.repository

import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.common.model.asResult
import com.apeun.gidaechi.common.utiles.DispatcherType
import com.apeun.gidaechi.common.utiles.SeugiDispatcher
import com.apeun.gidaechi.data.core.mapper.toModels
import com.apeun.gidaechi.data.core.model.ChatRoomModel
import com.apeun.gidaechi.data.personalchat.PersonalChatDataSource
import com.apeun.gidaechi.data.personalchat.PersonalChatRepository
import com.apeun.gidaechi.network.core.response.safeResponse
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
}