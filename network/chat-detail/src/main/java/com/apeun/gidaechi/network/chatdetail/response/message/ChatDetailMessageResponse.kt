package com.apeun.gidaechi.network.chatdetail.response.message

import com.apeun.gidaechi.network.chatdetail.response.ChatDetailTypeResponse
import java.time.LocalDateTime

data class ChatDetailMessageResponse(
    val id: String,
    val chatRoomId: Int,
    override val type: String,
    val author: ChatDetailMessageUserResponse,
    val eventList: List<Int>,
    val message: String,
    val emojiList: List<ChatDetailMessageEmojiResponse>,
    val mention: List<Int>,
    val mentionAll: Boolean,
    val timestamp: LocalDateTime,
    val read: List<Int>,
    val joined: List<Int>,
    val messageStatus: String
): ChatDetailTypeResponse()