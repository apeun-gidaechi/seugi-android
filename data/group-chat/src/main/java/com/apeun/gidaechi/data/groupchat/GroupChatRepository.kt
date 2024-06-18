package com.apeun.gidaechi.data.groupchat

import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.data.core.model.ChatRoomModel
import kotlinx.coroutines.flow.Flow

interface GroupChatRepository {

    suspend fun getGroupRoomList(workspaceId: String): Flow<Result<List<ChatRoomModel>>>
}
