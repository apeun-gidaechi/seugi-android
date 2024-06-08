package com.apeun.gidaechi.message.mapper

import com.apeun.gidaechi.message.model.message.MessageUserModel
import com.apeun.gidaechi.message.model.room.MessageRoomModel
import com.apeun.gidaechi.message.model.room.MessageRoomStatusType
import com.apeun.gidaechi.message.model.room.MessageRoomType
import com.apeun.gidaechi.network.message.response.room.MessageRoomResponse

internal fun MessageRoomResponse.toModel() =
    MessageRoomModel(
        id = id,
        workspaceId = workspaceID,
        type = type.toMessageRoomType(),
        roomAdmin = roomAdmin,
        chatName = chatName,
        chatRoomImg = chatRoomImg,
        createdAt = createdAt,
        memberList = joinUserId.map {
            MessageUserModel(
                id = it.id,
                name = it.name,
                profile = null
            )
        },
        chatStatusEnum = chatStatusEnum.toMessageRoomStatus()
    )

internal fun String.toMessageRoomType() =
    when(this) {
        "PERSONAL" -> MessageRoomType.PERSONAL
        else -> MessageRoomType.GROUP
    }

internal fun String.toMessageRoomStatus() =
    when(this) {
        "ALIVE" -> MessageRoomStatusType.ALIVE
        else -> MessageRoomStatusType.DELETE
    }