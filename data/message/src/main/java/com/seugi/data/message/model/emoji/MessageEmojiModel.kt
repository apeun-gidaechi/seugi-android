package com.seugi.data.message.model.emoji

import com.seugi.data.message.model.MessageType
import com.seugi.data.message.model.MessageTypeModel

data class MessageEmojiModel(
    override val type: MessageType,
    val eventList: List<Int>,
    val messageId: String,
    val emojiId: Int,
) : MessageTypeModel()
