package com.apeun.gidaechi.message.model.message

import com.apeun.gidaechi.message.model.MessageLifeType
import com.apeun.gidaechi.message.model.MessageType
import com.apeun.gidaechi.message.model.MessageTypeModel
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDateTime

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
