package com.apeun.gidaechi.message.model.message

import com.apeun.gidaechi.message.model.MessageTypeModel
import com.apeun.gidaechi.message.model.MessageLifeType
import com.apeun.gidaechi.message.model.MessageType
import java.time.LocalDateTime

data class MessageMessageModel(
    val id: String,
    val chatRoomId: String,
    override val type: MessageType,
    val author: Int,
    val message: String,
    val emojiList: List<MessageEmojiModel>,
    val mention: List<Int>,
    val mentionAll: Boolean,
    val timestamp: LocalDateTime,
    val read: List<Int>,
    val joined: List<Int>,
    val messageStatus: MessageLifeType
): MessageTypeModel()