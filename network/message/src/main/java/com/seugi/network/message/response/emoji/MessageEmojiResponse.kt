package com.seugi.network.message.response.emoji

import com.seugi.network.message.response.MessageTypeResponse

data class MessageEmojiResponse(
    val userId: List<Int>,
    val emojiId: Int,
)
