package com.apeun.gidaechi.network.chatdetail.response.message

import com.apeun.gidaechi.network.chatdetail.response.ChatDetailTypeResponse

data class ChatDetailMessageEmojiResponse(
    override val type: String,
    val emojiId: Int,
    val userId: List<Int>,
): ChatDetailTypeResponse()