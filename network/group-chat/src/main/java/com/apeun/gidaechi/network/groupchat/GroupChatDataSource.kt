package com.apeun.gidaechi.network.groupchat

import com.apeun.gidaechi.network.core.response.BaseResponse
import com.apeun.gidaechi.network.core.response.ChatRoomResponse

interface GroupChatDataSource {

    suspend fun getGroupRoomList(workspaceId: String): BaseResponse<List<ChatRoomResponse>>

    suspend fun createChat(workspaceId: String, roomName: String, joinUsers: List<Int>, chatRoomImg: String): BaseResponse<String>
}
