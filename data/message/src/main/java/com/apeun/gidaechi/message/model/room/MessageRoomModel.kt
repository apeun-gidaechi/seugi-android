package com.apeun.gidaechi.message.model.room

import com.apeun.gidaechi.message.model.message.MessageUserModel
import java.time.LocalDateTime

data class MessageRoomModel(
    val id: String,
    val workspaceId: String,
    val type: MessageRoomType,
    val chatName: String,
    val roomAdmin: Int,
    val createdAt: LocalDateTime,
    val chatRoomImg: String,
    val chatStatusEnum: MessageRoomStatusType,
    val memberList: List<MessageUserModel>,
)