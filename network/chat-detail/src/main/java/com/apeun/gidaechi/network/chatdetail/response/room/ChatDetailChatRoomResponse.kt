package com.apeun.gidaechi.network.chatdetail.response.room

import java.time.LocalDateTime

data class ChatDetailChatRoomResponse(
    val id: Int,
    val type: String,
    val chatName: String,
    val containUserCnt: Int,
    val chatRoomImg: String,
    val createdAt: LocalDateTime,
    val chatStatusEnum: String
)
