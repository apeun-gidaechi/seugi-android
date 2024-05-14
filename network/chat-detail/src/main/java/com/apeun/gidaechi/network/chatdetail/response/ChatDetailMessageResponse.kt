package com.apeun.gidaechi.network.chatdetail.response

import java.time.LocalDateTime

data class ChatDetailMessageResponse(
    val id: String,
    val chatRoomId: Int,
    val type: String,
    val author: ChatDetailUserResponse,
    val message: String,
    val emojiList: List<ChatDetailEmojiResponse>,
    val mention: List<Int>,
    val mentionAll: Boolean,
    val timestamp: LocalDateTime,
    val read: List<Int>,
    val joined: List<Int>,
    val messageStatus: String
)