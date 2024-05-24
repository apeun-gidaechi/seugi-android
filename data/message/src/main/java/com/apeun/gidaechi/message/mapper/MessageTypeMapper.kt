package com.apeun.gidaechi.message.mapper

import com.apeun.gidaechi.message.model.MessageType
import com.apeun.gidaechi.network.message.response.MessageTypeResponse
import com.apeun.gidaechi.network.message.response.emoji.MessageEmojiResponse
import com.apeun.gidaechi.network.message.response.message.MessageDeleteResponse
import com.apeun.gidaechi.network.message.response.message.MessageMessageResponse
import com.apeun.gidaechi.network.message.response.sub.MessageSubResponse

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

internal fun MessageTypeResponse.toModel() =
    when (this.type) {
        "MESSAGE", "FILE", "IMG", "ENTER", "LEFT" -> (this as MessageMessageResponse).toModel()
        "SUB" -> (this as MessageSubResponse).toModel()
        "DELETE_MESSAGE" -> (this as MessageDeleteResponse).toModel()
        "ADD_EMOJI", "REMOVE_EMOJI" -> (this as MessageEmojiResponse).toModel()
        else -> {
            throw RuntimeException()
        }
    }