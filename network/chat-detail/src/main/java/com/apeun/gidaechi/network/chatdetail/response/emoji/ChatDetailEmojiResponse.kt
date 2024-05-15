package com.apeun.gidaechi.network.chatdetail.response.emoji

import com.apeun.gidaechi.network.chatdetail.response.ChatDetailTypeResponse

data class ChatDetailEmojiResponse(
    override val type: String,
    val eventList: List<Int>,
    val messageId: String,
    val emojiId : Int
): ChatDetailTypeResponse()