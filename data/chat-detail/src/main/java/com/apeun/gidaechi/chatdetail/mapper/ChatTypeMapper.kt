package com.apeun.gidaechi.chatdetail.mapper

import com.apeun.gidaechi.chatdetail.model.ChatType

fun String.toChatType(): ChatType =
    when(this) {
        "MESSAGE" -> ChatType.MESSAGE
        "FILE" -> ChatType.FILE
        "IMG" -> ChatType.IMG
        "ENTER" -> ChatType.ENTER
        "LEFT" -> ChatType.LEFT
        "SUB" -> ChatType.SUB
        "UNSUB" -> ChatType.UNSUB
        else -> ChatType.MESSAGE
    }