package com.apeun.gidaechi.data.core.model

import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDateTime

data class ChatRoomModel(
    val id: String,
    val workspaceId: String,
    val type: ChatRoomType,
    val chatName: String,
    val roomAdmin: Int,
    val createdAt: LocalDateTime,
    val chatRoomImg: String,
    val chatStatusEnum: ChatRoomStatusType,
    val memberList: ImmutableList<UserModel>,
    val lastMessage: String,
    val lastMessageTimestamp: LocalDateTime,
    val notReadCnt: Int,
)
