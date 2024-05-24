package com.apeun.gidaechi.message.mapper

import com.apeun.gidaechi.message.model.room.MessageRoomStatusType
import com.apeun.gidaechi.message.model.room.MessageRoomType


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