package com.apeun.gidaechi.data.personalchat.model

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
    val memberList: List<PersonalChatUserModel>,
    val lastMessage: String,
    val lastMessageTimestamp: LocalDateTime,
)
