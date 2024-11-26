package com.seugi.network.message.response.message

import com.seugi.network.message.response.MessageTypeResponse

data class MessageEmojiResponse(
    override val type: String,
    val emojiId: Int,
    val userId: List<Long>,
) : MessageTypeResponse()
