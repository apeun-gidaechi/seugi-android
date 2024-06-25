package com.apeun.gidaechi.data.groupchat

import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.data.core.model.ChatRoomModel
import com.apeun.gidaechi.network.core.response.BaseResponse
import kotlinx.coroutines.flow.Flow

interface GroupChatRepository {

    suspend fun getGroupRoomList(workspaceId: String): Flow<Result<List<ChatRoomModel>>>

    suspend fun createChat(workspaceId: String, roomName: String, joinUsers: List<Int>, chatRoomImg: String): Flow<Result<String>>
}
