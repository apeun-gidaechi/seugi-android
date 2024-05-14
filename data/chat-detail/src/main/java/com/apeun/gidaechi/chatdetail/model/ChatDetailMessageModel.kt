package com.apeun.gidaechi.chatdetail.model

import java.time.LocalDateTime

data class ChatDetailMessageModel(
    val id: String,
    val chatRoomId: Int,
    val type: ChatType,
    val author: ChatDetailUserModel,
    val message: String,
    val emojiList: List<ChatDetailEmojiModel>,
    val mention: List<Int>,
    val mentionAll: Boolean,
    val timestamp: LocalDateTime,
    val read: List<Int>,
    val joined: List<Int>,
    val messageStatus: ChatMessageType
)