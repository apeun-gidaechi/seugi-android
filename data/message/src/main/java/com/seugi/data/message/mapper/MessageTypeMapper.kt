package com.seugi.data.message.mapper

import com.seugi.data.message.model.MessageType
import com.seugi.network.message.response.MessageTypeResponse
import com.seugi.network.message.response.emoji.MessageEmojiResponse
import com.seugi.network.message.response.message.MessageDeleteResponse
import com.seugi.network.message.response.message.MessageMessageResponse
import com.seugi.network.message.response.sub.MessageSubResponse

internal fun String.toMessageType(): MessageType = when (this) {
    "MESSAGE" -> MessageType.MESSAGE
    "FILE" -> MessageType.FILE
    "IMG" -> MessageType.IMG
    "ENTER" -> MessageType.ENTER
    "LEFT" -> MessageType.LEFT
    "SUB" -> MessageType.SUB
    "DELETE_MESSAGE" -> MessageType.DELETE_MESSAGE
    "ADD_EMOJI" -> MessageType.ADD_EMOJI
    "REMOVE_EMOJI" -> MessageType.REMOVE_EMOJI
    "TRANSFER_ADMIN" -> MessageType.TRANSFER_ADMIN
    else -> MessageType.MESSAGE
}