package com.apeun.gidaechi.chatdatail.mapper

import com.apeun.gidaechi.chatdatail.model.ChatDetailChatTypeState
import com.apeun.gidaechi.chatdatail.model.ChatDetailMessageState
import com.apeun.gidaechi.chatdetail.model.ChatDetailTypeModel
import com.apeun.gidaechi.chatdetail.model.ChatType
import com.apeun.gidaechi.chatdetail.model.message.ChatDetailMessageModel

internal fun ChatDetailMessageModel.toState(
    isFirst: Boolean,
    isLast: Boolean,
    isMe: Boolean
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
internal fun ChatType.toState() =
    when(this) {
        ChatType.MESSAGE -> ChatDetailChatTypeState.MESSAGE
        ChatType.FILE -> ChatDetailChatTypeState.MESSAGE
        ChatType.IMG -> ChatDetailChatTypeState.AI
        ChatType.LEFT -> ChatDetailChatTypeState.LEFT
        ChatType.ENTER -> ChatDetailChatTypeState.ENTER
        else -> {
            throw RuntimeException()
        }
    }