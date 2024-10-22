package com.seugi.network.groupchat

import com.seugi.common.model.Result
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.ChatRoomResponse
import kotlinx.coroutines.flow.Flow

interface GroupChatDataSource {

    suspend fun getGroupRoomList(workspaceId: String): BaseResponse<List<ChatRoomResponse>>

    suspend fun createChat(workspaceId: String, roomName: String, joinUsers: List<Long>, chatRoomImg: String): BaseResponse<String>

    suspend fun getChat(roomId: String): BaseResponse<ChatRoomResponse>

    suspend fun leftRoom(chatRoomId: String): BaseResponse<Unit?>

    suspend fun addMembers(chatRoomId: String, chatMemberUsers: List<Long>): BaseResponse<Unit?>
}
