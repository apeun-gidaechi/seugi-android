package com.seugi.network.message.request

data class EmojiRequest(
    val messageId: String,
    val roomId: String,
    val emojiId: Int
)