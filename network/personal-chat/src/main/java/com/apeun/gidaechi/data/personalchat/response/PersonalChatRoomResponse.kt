package com.seugi.data.personalchat.response

import com.seugi.network.core.response.UserResponse
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
    val joinUserId: List<UserResponse>,
    val lastMessage: String,
    val lastMessageTimestamp: LocalDateTime,
    val notReadCnt: Int,
)
