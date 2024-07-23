package com.seugi.data.message.model.message

import com.seugi.data.message.model.MessageLifeType
import com.seugi.data.message.model.MessageType
import com.seugi.data.message.model.MessageTypeModel
import java.time.LocalDateTime
import kotlinx.collections.immutable.ImmutableList

data class MessageMessageModel(
    val id: String,
    val chatRoomId: String,
    override val type: MessageType,
    val author: Int,
    val message: String,
    val emojiList: ImmutableList<MessageEmojiModel>,
    val mention: ImmutableList<Int>,
    val mentionAll: Boolean,
    val timestamp: LocalDateTime,
    val read: ImmutableList<Int>,
    val messageStatus: MessageLifeType,
) : MessageTypeModel()
