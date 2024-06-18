package com.apeun.gidaechi.data.personalchat

import com.apeun.gidaechi.network.core.response.BaseResponse
import com.apeun.gidaechi.network.core.response.ChatRoomResponse

interface PersonalChatDataSource {

    suspend fun getAllChat(workspaceId: String): BaseResponse<List<ChatRoomResponse>>
}
