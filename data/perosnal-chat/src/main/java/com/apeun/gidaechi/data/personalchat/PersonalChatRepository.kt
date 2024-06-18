package com.apeun.gidaechi.data.personalchat

import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.data.core.model.ChatRoomModel
import kotlinx.coroutines.flow.Flow

interface PersonalChatRepository {

    suspend fun getAllChat(workspaceId: String): Flow<Result<List<ChatRoomModel>>>
}
