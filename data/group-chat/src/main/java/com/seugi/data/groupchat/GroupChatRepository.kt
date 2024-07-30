package com.seugi.data.groupchat

import com.seugi.common.model.Result
import com.seugi.data.core.model.ChatRoomModel
import kotlinx.coroutines.flow.Flow

interface GroupChatRepository {

    suspend fun getGroupRoomList(workspaceId: String): Flow<Result<List<ChatRoomModel>>>

    suspend fun createChat(workspaceId: String, roomName: String, joinUsers: List<Int>, chatRoomImg: String): Flow<Result<String>>
}