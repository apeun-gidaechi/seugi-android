package com.apeun.gidaechi.data.personalchat.response

import java.time.LocalDateTime

data class PersonalChatRoomResponse(
    val id: String,
    val workspaceId: String,
    val type: String,
    val chatName: String,
    val roomAdmin: Int,
    val chatRoomImg: String,
    val createdAt: LocalDateTime,
    val chatStatusEnum: String,
    val joinUserId: List<PersonalChatUserResponse>,
)
