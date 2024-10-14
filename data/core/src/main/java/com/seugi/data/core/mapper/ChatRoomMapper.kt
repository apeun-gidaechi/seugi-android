package com.seugi.data.core.mapper

import com.seugi.data.core.model.ChatRoomModel
import com.seugi.data.core.model.ChatRoomStatusType
import com.seugi.data.core.model.ChatRoomType
import com.seugi.network.core.response.ChatRoomResponse
import kotlinx.collections.immutable.toImmutableList

fun List<ChatRoomResponse>.toModels() = this.map {
    it.toModel()
}

fun ChatRoomResponse.toModel() = ChatRoomModel(
    id = id,
    workspaceId = workspaceId,
    type = type.toChatRoomType(),
    roomAdmin = roomAdmin,
    chatName = chatName,
    chatRoomImg = chatRoomImg,
    createdAt = createdAt,
    memberList = joinUserInfo.map {
        it.toModel()
    }.toImmutableList(),
    chatStatusEnum = chatStatusEnum.toChatRoomStatusType(),
    lastMessage = lastMessage,
    lastMessageTimestamp = lastMessageTimestamp,
    notReadCnt = notReadCnt,
)

internal fun String.toChatRoomType() = when (this) {
    "PERSONAL" -> ChatRoomType.PERSONAL
    else -> ChatRoomType.GROUP
}

internal fun String.toChatRoomStatusType() = when (this) {
    "ALIVE" -> ChatRoomStatusType.ALIVE
    else -> ChatRoomStatusType.DELETE
}
