package com.seugi.data.message.mapper

import com.seugi.data.message.model.MessageLifeType

internal fun String.toMessageLifeType(): MessageLifeType = when (this) {
    "ALIVE" -> MessageLifeType.ALIVE
    "DELETE" -> MessageLifeType.DELETE
    else -> MessageLifeType.DELETE
}
