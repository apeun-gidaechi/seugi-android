package com.apeun.gidaechi.chatdetail.mapper

import com.apeun.gidaechi.chatdetail.model.emoji.ChatDetailEmojiModel
import com.apeun.gidaechi.network.chatdetail.response.emoji.ChatDetailEmojiResponse


internal fun ChatDetailEmojiResponse.toModel() =
    ChatDetailEmojiModel(
        type = type.toChatType(),
        eventList = eventList,
        messageId = messageId,
        emojiId = emojiId
    )