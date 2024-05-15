package com.apeun.gidaechi.chatdetail.mapper

import com.apeun.gidaechi.chatdetail.model.ChatMessageType
import com.apeun.gidaechi.chatdetail.model.ChatType

internal fun String.toChatMessageType(): ChatMessageType =
    when(this) {
        "ALIVE" -> ChatMessageType.ALIVE
        "DELETE" -> ChatMessageType.DELETE
        else -> ChatMessageType.DELETE
    }