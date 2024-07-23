package com.seugi.chatdatail.mapper

import com.seugi.chatdatail.model.ChatDetailChatTypeState
import com.seugi.chatdatail.model.ChatDetailMessageState
import com.seugi.data.message.model.MessageType
import com.seugi.data.message.model.message.MessageMessageModel
import com.seugi.data.message.model.message.MessageUserModel

internal fun MessageMessageModel.toState(isFirst: Boolean, isLast: Boolean, isMe: Boolean, author: MessageUserModel) = ChatDetailMessageState(
    id = id,
    chatRoomId = chatRoomId,
    type = type.toState(),
    author = author,
    message = message,
    emojiList = emojiList,
    mention = mention,
    mentionAll = mentionAll,
    timestamp = timestamp,
    read = read,
    messageStatus = messageStatus,
    isFirst = isFirst,
    isLast = isLast,
    isMe = isMe,
)

/**
 * ChatType To ChatTypeState
 * Is not message type on throw RuntimeException
 */
internal fun MessageType.toState() = when (this) {
    MessageType.MESSAGE -> ChatDetailChatTypeState.MESSAGE
    MessageType.FILE -> ChatDetailChatTypeState.MESSAGE
    MessageType.IMG -> ChatDetailChatTypeState.AI
    MessageType.LEFT -> ChatDetailChatTypeState.LEFT
    MessageType.ENTER -> ChatDetailChatTypeState.ENTER
    else -> {
        throw RuntimeException()
    }
}
