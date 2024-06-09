package com.apeun.gidaechi.network.message.response.message

import com.apeun.gidaechi.network.message.response.MessageTypeResponse

data class MessageEmojiResponse(
    override val type: String,
    val emojiId: Int,
    val userId: List<Int>,
) : MessageTypeResponse()
