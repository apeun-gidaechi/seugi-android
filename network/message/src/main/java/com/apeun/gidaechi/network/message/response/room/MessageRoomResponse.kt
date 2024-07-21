package com.seugi.network.message.response.room

import java.time.LocalDateTime

data class MessageRoomResponse(
    val id: String,
    val workspaceId: String,
    val type: String,
    val chatName: String,
    val roomAdmin: Int,
    val chatRoomImg: String?,
    val createdAt: LocalDateTime,
    val chatStatusEnum: String,
    val joinUserId: List<MessageRoomUserResponse>,
)
