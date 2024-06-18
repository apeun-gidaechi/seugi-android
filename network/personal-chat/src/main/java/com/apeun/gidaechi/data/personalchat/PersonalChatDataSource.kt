package com.apeun.gidaechi.data.personalchat

import com.apeun.gidaechi.data.personalchat.response.PersonalChatRoomResponse
import com.apeun.gidaechi.network.core.response.BaseResponse

interface PersonalChatDataSource {

    suspend fun getAllChat(workspaceId: String): BaseResponse<List<PersonalChatRoomResponse>>
}
