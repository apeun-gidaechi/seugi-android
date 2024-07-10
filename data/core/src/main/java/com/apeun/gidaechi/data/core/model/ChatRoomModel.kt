package com.apeun.gidaechi.data.core.model

import java.time.LocalDateTime
import kotlinx.collections.immutable.ImmutableList

data class ChatRoomModel(
    val id: String,
    val workspaceId: String,
    val type: ChatRoomType,
    val chatName: String,
    val roomAdmin: Int,
    val createdAt: LocalDateTime,
    val chatRoomImg: String?,
    val chatStatusEnum: ChatRoomStatusType,
    val memberList: ImmutableList<UserModel>,
    val lastMessage: String?,
    val lastMessageTimestamp: LocalDateTime?,
    val notReadCnt: Int,
)
