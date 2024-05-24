package com.apeun.gidaechi.network.message.response.message

import com.apeun.gidaechi.network.message.response.MessageTypeResponse
import java.time.LocalDateTime

data class MessageMessageResponse(
    val id: String,
    val chatRoomId: Int,
    override val type: String,
    val author: MessageUserResponse,
    val eventList: List<Int>,
    val message: String,
    val emojiList: List<MessageEmojiResponse>,
    val mention: List<Int>,
    val mentionAll: Boolean,
    val timestamp: LocalDateTime,
    val read: List<Int>,
    val joined: List<Int>,
    val messageStatus: String
): MessageTypeResponse()