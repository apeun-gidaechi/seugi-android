package com.apeun.gidaechi.data.core.mapper

import com.apeun.gidaechi.data.core.model.ChatRoomModel
import com.apeun.gidaechi.data.core.model.ChatRoomStatusType
import com.apeun.gidaechi.data.core.model.ChatRoomType
import com.apeun.gidaechi.network.core.response.ChatRoomResponse

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
    memberList = joinUserId.map {
        it.toModel()
    },
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