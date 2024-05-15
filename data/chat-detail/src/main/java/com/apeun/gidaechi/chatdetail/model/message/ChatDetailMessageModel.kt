package com.apeun.gidaechi.chatdetail.model.message

import com.apeun.gidaechi.chatdetail.model.ChatDetailTypeModel
import com.apeun.gidaechi.chatdetail.model.ChatMessageType
import com.apeun.gidaechi.chatdetail.model.ChatType
import java.time.LocalDateTime

data class ChatDetailMessageModel(
    val id: String,
    val chatRoomId: Int,
    override val type: ChatType,
    val author: ChatDetailMessageUserModel,
    val message: String,
    val emojiList: List<ChatDetailMessageEmojiModel>,
    val mention: List<Int>,
    val mentionAll: Boolean,
    val timestamp: LocalDateTime,
    val read: List<Int>,
    val joined: List<Int>,
    val messageStatus: ChatMessageType
): ChatDetailTypeModel()