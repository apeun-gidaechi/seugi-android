package com.apeun.gidaechi.network.chatdetail.response.room

data class ChatDetailRoomMemberResponse(
    val chatRoomId: Int,
    val workspaceId: String,
    val roomType: String,
    val roomAdmin: Int?,
    val joinUserId: List<Int>
)