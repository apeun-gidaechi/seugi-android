package com.seugi.data.message.mapper

import com.seugi.data.message.model.message.MessageUserModel
import com.seugi.data.message.model.room.MessageRoomModel
import com.seugi.data.message.model.room.MessageRoomStatusType
import com.seugi.data.message.model.room.MessageRoomType
import com.seugi.network.message.response.room.MessageRoomResponse
import kotlinx.collections.immutable.toImmutableList

internal fun MessageRoomResponse.toModel() = MessageRoomModel(
    id = id,
    workspaceId = workspaceId,
    type = type.toMessageRoomType(),
    roomAdmin = roomAdmin,
    chatName = chatName,
    chatRoomImg = chatRoomImg,
    createdAt = createdAt,
    memberList = joinUserId.map {
        MessageUserModel(
            id = it.id,
            name = it.name,
            profile = null,
        )
    }.toImmutableList(),
    chatStatusEnum = chatStatusEnum.toMessageRoomStatus(),
)

internal fun String.toMessageRoomType() = when (this) {
    "PERSONAL" -> MessageRoomType.PERSONAL
    else -> MessageRoomType.GROUP
}

internal fun String.toMessageRoomStatus() = when (this) {
    "ALIVE" -> MessageRoomStatusType.ALIVE
    else -> MessageRoomStatusType.DELETE
}
