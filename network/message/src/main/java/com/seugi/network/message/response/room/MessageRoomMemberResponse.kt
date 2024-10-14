package com.seugi.network.message.response.room

data class MessageRoomMemberResponse(
    val chatRoomId: Int,
    val workspaceId: String,
    val roomType: String,
    val roomAdmin: Int?,
    val joinUserId: List<Int>,
)
