package com.seugi.data.core.model

import java.time.LocalDateTime
import kotlinx.collections.immutable.ImmutableList

data class ChatRoomModel(
    val id: String,
    val workspaceId: String,
    val type: ChatRoomType,
    val roomAdmin: Long,
    val chatName: String,
    val createdAt: LocalDateTime,
    val chatRoomImg: String?,
    val chatStatusEnum: ChatRoomStatusType,
    val memberList: ImmutableList<UserInfoModel>,
    val lastMessage: String?,
    val lastMessageTimestamp: LocalDateTime?,
    val notReadCnt: Int,
)
