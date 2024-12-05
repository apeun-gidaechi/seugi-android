package com.seugi.network.core.response

import java.time.LocalDateTime

data class ChatRoomResponse(
    val id: String,
    val workspaceId: String,
    val type: String,
    val roomAdmin: Long,
    val chatName: String?,
    val chatRoomImg: String?,
    val createdAt: LocalDateTime,
    val chatStatusEnum: String,
    val joinUserInfo: List<UserInfoResponse>,
    val lastMessage: String?,
    val lastMessageTimestamp: LocalDateTime?,
    val notReadCnt: Int,
)
