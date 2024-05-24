package com.apeun.gidaechi.message.mapper

import com.apeun.gidaechi.message.model.MessageType
import com.apeun.gidaechi.network.chatdetail.response.ChatDetailTypeResponse
import com.apeun.gidaechi.network.chatdetail.response.emoji.ChatDetailEmojiResponse
import com.apeun.gidaechi.network.chatdetail.response.message.ChatDetailMessageDeleteResponse
import com.apeun.gidaechi.network.chatdetail.response.message.ChatDetailMessageResponse
import com.apeun.gidaechi.network.chatdetail.response.sub.ChatDetailSubResponse

internal fun String.toMessageType(): MessageType =
    when(this) {
        "MESSAGE" -> MessageType.MESSAGE
        "FILE" -> MessageType.FILE
        "IMG" -> MessageType.IMG
        "ENTER" -> MessageType.ENTER
        "LEFT" -> MessageType.LEFT
        "SUB" -> MessageType.SUB
        "DELETE_MESSAGE" -> MessageType.DELETE_MESSAGE
        "ADD_EMOJI" -> MessageType.ADD_EMOJI
        "REMOVE_EMOJI" -> MessageType.REMOVE_EMOJI
        else -> MessageType.MESSAGE
    }

internal fun ChatDetailTypeResponse.toModel() =
    when (this.type) {
        "MESSAGE", "FILE", "IMG", "ENTER", "LEFT" -> (this as ChatDetailMessageResponse).toModel()
        "SUB" -> (this as ChatDetailSubResponse).toModel()
        "DELETE_MESSAGE" -> (this as ChatDetailMessageDeleteResponse).toModel()
        "ADD_EMOJI", "REMOVE_EMOJI" -> (this as ChatDetailEmojiResponse).toModel()
        else -> {
            throw RuntimeException()
        }
    }