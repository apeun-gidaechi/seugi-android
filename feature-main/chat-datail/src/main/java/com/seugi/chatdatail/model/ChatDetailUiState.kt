package com.seugi.chatdatail.model

import com.seugi.data.message.model.MessageLifeType
import com.seugi.data.message.model.message.MessageEmojiModel
import com.seugi.data.message.model.message.MessageUserModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import java.time.LocalDateTime

data class ChatDetailUiState(
    val roomInfo: ChatRoomState? = null,
    val userInfo: MessageUserModel? = null,
    val nowPage: Int = 0,
    val isInit: Boolean = false,
    val isLastPage: Boolean = false,
    val message: ImmutableList<ChatDetailMessageState> = persistentListOf(),
    val users: ImmutableMap<Int, MessageUserModel> = persistentMapOf(),
)

data class ChatRoomState(
    val id: String,
    val roomName: String,
    val members: ImmutableList<MessageUserModel> = persistentListOf(),
)

data class ChatDetailMessageState(
    val id: String,
    val chatRoomId: String,
    val type: ChatDetailChatTypeState,
    val author: MessageUserModel,
    val message: String,
    val emojiList: ImmutableList<MessageEmojiModel>,
    val mention: ImmutableList<Int>,
    val mentionAll: Boolean,
    val timestamp: LocalDateTime,
    val read: ImmutableList<Int>,
    val messageStatus: MessageLifeType,
    val isFirst: Boolean,
    val isLast: Boolean,
    val isMe: Boolean,
) {
    constructor(chatRoomId: String, type: ChatDetailChatTypeState, timestamp: LocalDateTime) : this(
        id = "id",
        chatRoomId = chatRoomId,
        type = type,
        author = MessageUserModel(0, "qwe", null),
        message = "",
        emojiList = persistentListOf(),
        mention = persistentListOf(),
        mentionAll = false,
        timestamp = timestamp,
        read = persistentListOf(),
        messageStatus = MessageLifeType.ALIVE,
        isFirst = false,
        isLast = false,
        isMe = false,
    )
}

sealed interface ChatDetailSideEffect {
    data object SuccessLeft : ChatDetailSideEffect
    data class FailedLeft(val throwable: Throwable) : ChatDetailSideEffect
}
