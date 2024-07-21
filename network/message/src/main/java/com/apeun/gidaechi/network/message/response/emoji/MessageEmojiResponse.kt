package com.seugi.network.message.response.emoji

import com.seugi.network.message.response.MessageTypeResponse

data class MessageEmojiResponse(
    override val type: String,
    val eventList: List<Int>,
    val messageId: String,
    val emojiId: Int,
) : MessageTypeResponse()
