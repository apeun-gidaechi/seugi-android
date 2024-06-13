package com.apeun.gidaechi.message.model.emoji

import com.apeun.gidaechi.message.model.MessageType
import com.apeun.gidaechi.message.model.MessageTypeModel

data class MessageEmojiModel(
    override val type: MessageType,
    val eventList: List<Int>,
    val messageId: String,
    val emojiId: Int,
) : MessageTypeModel()
