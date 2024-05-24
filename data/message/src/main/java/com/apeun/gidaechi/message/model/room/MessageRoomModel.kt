package com.apeun.gidaechi.message.model.room

import java.time.LocalDateTime

data class MessageRoomModel(
    val id: Int,
    val type: MessageRoomType,
    val chatName: String,
    val containUserCnt: Int,
    val chatRoomImg: String,
    val createdAt: LocalDateTime,
    val workspaceId: String,
    val memberList: List<Int>,
    val chatStatusEnum: MessageRoomStatusType
)