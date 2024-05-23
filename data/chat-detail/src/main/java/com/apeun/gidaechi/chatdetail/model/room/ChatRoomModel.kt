package com.apeun.gidaechi.chatdetail.model.room

import java.time.LocalDateTime

data class ChatRoomModel(
    val id: Int,
    val type: ChatRoomType,
    val chatName: String,
    val containUserCnt: Int,
    val chatRoomImg: String,
    val createdAt: LocalDateTime,
    val workspaceId: String,
    val memberList: List<Int>,
    val chatStatusEnum: ChatRoomStatusType
)