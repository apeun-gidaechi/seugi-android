package com.apeun.gidaechi.chatdatail.model

import com.apeun.gidaechi.message.model.MessageLifeType
import com.apeun.gidaechi.message.model.message.MessageEmojiModel
import com.apeun.gidaechi.message.model.message.MessageUserModel
import java.time.LocalDateTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ChatDetailUiState(
    val roomInfo: ChatRoomState? = null,
    val userInfo: MessageUserModel? = null,
    val nowPage: Int = 0,
    val isInit: Boolean = false,
    val isLastPage: Boolean = false,
    val message: ImmutableList<ChatDetailMessageState> = persistentListOf(),
)

data class ChatRoomState(
    val id: Int,
    val roomName: String,
    val members: ImmutableList<MessageUserModel> = persistentListOf(),
)

data class ChatDetailMessageState(
    val id: String,
    val chatRoomId: Int,
    val type: ChatDetailChatTypeState,
    val author: MessageUserModel,
    val message: String,
    val emojiList: List<MessageEmojiModel>,
    val mention: List<Int>,
    val mentionAll: Boolean,
    val timestamp: LocalDateTime,
    val read: List<Int>,
    val joined: List<Int>,
    val messageStatus: MessageLifeType,
    val isFirst: Boolean,
    val isLast: Boolean,
    val isMe: Boolean
) {
    constructor(chatRoomId: Int, type: ChatDetailChatTypeState, timestamp: LocalDateTime): this(
        id = "id",
        chatRoomId = chatRoomId,
        type = type,
        author = MessageUserModel(0, "qwe", null),
        message = "",
        emojiList = emptyList(),
        mention = emptyList(),
        mentionAll = false,
        timestamp = timestamp,
        read = emptyList(),
        joined = emptyList(),
        messageStatus = MessageLifeType.ALIVE,
        isFirst = false,
        isLast = false,
        isMe = false
    )
}