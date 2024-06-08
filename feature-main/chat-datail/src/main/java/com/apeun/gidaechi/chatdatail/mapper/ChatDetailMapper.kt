package com.apeun.gidaechi.chatdatail.mapper

import com.apeun.gidaechi.chatdatail.model.ChatDetailChatTypeState
import com.apeun.gidaechi.chatdatail.model.ChatDetailMessageState
import com.apeun.gidaechi.message.model.MessageType
import com.apeun.gidaechi.message.model.message.MessageMessageModel
import com.apeun.gidaechi.message.model.message.MessageUserModel

internal fun MessageMessageModel.toState(
    isFirst: Boolean,
    isLast: Boolean,
    isMe: Boolean,
    author: MessageUserModel
) =
    ChatDetailMessageState(
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
        joined = joined,
        messageStatus = messageStatus,
        isFirst = isFirst,
        isLast = isLast,
        isMe = isMe
    )

/**
 * ChatType To ChatTypeState
 * Is not message type on throw RuntimeException
 */
internal fun MessageType.toState() =
    when(this) {
        MessageType.MESSAGE -> ChatDetailChatTypeState.MESSAGE
        MessageType.FILE -> ChatDetailChatTypeState.MESSAGE
        MessageType.IMG -> ChatDetailChatTypeState.AI
        MessageType.LEFT -> ChatDetailChatTypeState.LEFT
        MessageType.ENTER -> ChatDetailChatTypeState.ENTER
        else -> {
            throw RuntimeException()
        }
    }