package com.apeun.gidaechi.data.personalchat.model

import com.apeun.gidaechi.data.core.model.UserModel
import java.time.LocalDateTime

data class PersonalChatRoomModel(
    val id: String,
    val workspaceId: String,
    val type: PersonalChatRoomType,
    val chatName: String,
    val roomAdmin: Int,
    val createdAt: LocalDateTime,
    val chatRoomImg: String,
    val chatStatusEnum: PersonalChatRoomStatusType,
    val memberList: List<UserModel>,
    val lastMessage: String,
    val lastMessageTimestamp: LocalDateTime,
    val notReadCnt: Int,
)
