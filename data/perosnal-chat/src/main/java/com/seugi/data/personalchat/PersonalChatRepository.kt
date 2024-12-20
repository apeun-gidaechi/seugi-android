package com.seugi.data.personalchat

import com.seugi.common.model.Result
import com.seugi.data.core.model.ChatRoomModel
import kotlinx.coroutines.flow.Flow

interface PersonalChatRepository {

    suspend fun getAllChat(workspaceId: String): Flow<Result<List<ChatRoomModel>>>

    suspend fun createChat(workspaceId: String, roomName: String, joinUsers: List<Long>, chatRoomImg: String): Flow<Result<String>>

    suspend fun getChat(roomId: String): Flow<Result<ChatRoomModel>>
}
