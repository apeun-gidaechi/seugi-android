package com.apeun.gidaechi.network.message.response.room

import java.time.LocalDateTime

data class MessageRoomResponse(
    val id: Int,
    val type: String,
    val chatName: String,
    val containUserCnt: Int,
    val chatRoomImg: String,
    val createdAt: LocalDateTime,
    val chatStatusEnum: String
)
