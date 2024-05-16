package com.apeun.gidaechi.chatdatail.mapper

import com.apeun.gidaechi.chatdatail.model.ChatDetailMessageState
import com.apeun.gidaechi.chatdetail.model.message.ChatDetailMessageModel

internal fun ChatDetailMessageModel.toState(
    isFirst: Boolean,
    isLast: Boolean
) =
    ChatDetailMessageState(
        id = id,
        chatRoomId = chatRoomId,
        type = type,
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
        isLast = isLast
    )