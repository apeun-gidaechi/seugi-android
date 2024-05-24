package com.apeun.gidaechi.message.mapper

import com.apeun.gidaechi.message.model.MessageLifeType

internal fun String.toMessageLifeType(): MessageLifeType =
    when(this) {
        "ALIVE" -> MessageLifeType.ALIVE
        "DELETE" -> MessageLifeType.DELETE
        else -> MessageLifeType.DELETE
    }