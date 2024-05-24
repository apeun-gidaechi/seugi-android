package com.apeun.gidaechi.message.model.emoji

import com.apeun.gidaechi.message.model.MessageTypeModel
import com.apeun.gidaechi.message.model.MessageType

data class MessageEmojiModel(
    override val type: MessageType,
    val eventList: List<Int>,
    val messageId: String,
    val emojiId : Int
): MessageTypeModel()
