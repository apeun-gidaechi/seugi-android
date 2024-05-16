package com.apeun.gidaechi.chatdatail.model

import com.apeun.gidaechi.chatdetail.model.ChatMessageType
import com.apeun.gidaechi.chatdetail.model.ChatType
import com.apeun.gidaechi.chatdetail.model.message.ChatDetailMessageEmojiModel
import com.apeun.gidaechi.chatdetail.model.message.ChatDetailMessageModel
import com.apeun.gidaechi.chatdetail.model.message.ChatDetailMessageUserModel
import java.time.LocalDateTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ChatDetailUiState(
    val roomInfo: ChatRoomState? = null,
    val userInfo: ChatDetailMessageUserModel? = null,
    val message: ImmutableList<ChatDetailMessageState> = persistentListOf(),
)

data class ChatRoomState(
    val id: Int,
    val roomName: String,
    val members: ImmutableList<ChatDetailMessageUserModel> = persistentListOf(),
)

data class ChatDetailMessageState(
    val id: String,
    val chatRoomId: Int,
    val type: ChatType,
    val author: ChatDetailMessageUserModel,
    val message: String,
    val emojiList: List<ChatDetailMessageEmojiModel>,
    val mention: List<Int>,
    val mentionAll: Boolean,
    val timestamp: LocalDateTime,
    val read: List<Int>,
    val joined: List<Int>,
    val messageStatus: ChatMessageType,
    val isFirst: Boolean,
    val isLast: Boolean
)