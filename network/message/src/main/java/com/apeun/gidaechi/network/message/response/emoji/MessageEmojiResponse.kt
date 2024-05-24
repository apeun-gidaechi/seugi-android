package com.apeun.gidaechi.network.message.response.emoji

import com.apeun.gidaechi.network.message.response.MessageTypeResponse

data class MessageEmojiResponse(
    override val type: String,
    val eventList: List<Int>,
    val messageId: String,
    val emojiId : Int
): MessageTypeResponse()