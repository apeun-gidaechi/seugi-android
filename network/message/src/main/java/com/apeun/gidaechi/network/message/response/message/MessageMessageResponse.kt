package com.seugi.network.message.response.message

import com.seugi.network.message.response.MessageTypeResponse
import java.time.LocalDateTime

data class MessageMessageResponse(
    val id: String,
    val chatRoomId: String,
    override val type: String,
    val userId: Int,
    val message: String,
    val emoticon: String?,
    val eventList: List<Int>?,
    val emojiList: List<MessageEmojiResponse>,
    val mention: List<Int>,
    val mentionAll: Boolean,
    val timestamp: LocalDateTime,
    val read: List<Int>,
    val messageStatus: String,
) : MessageTypeResponse()
