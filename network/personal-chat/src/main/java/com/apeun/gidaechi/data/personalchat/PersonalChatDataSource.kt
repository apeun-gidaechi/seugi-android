package com.seugi.data.personalchat

import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.ChatRoomResponse

interface PersonalChatDataSource {

    suspend fun getAllChat(workspaceId: String): BaseResponse<List<ChatRoomResponse>>

    suspend fun createChat(workspaceId: String, roomName: String, joinUsers: List<Int>, chatRoomImg: String): BaseResponse<String>
}
