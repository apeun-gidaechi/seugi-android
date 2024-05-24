package com.apeun.gidaechi.message.mapper

import com.apeun.gidaechi.message.model.emoji.MessageEmojiModel
import com.apeun.gidaechi.network.chatdetail.response.emoji.ChatDetailEmojiResponse


internal fun ChatDetailEmojiResponse.toModel() =
    MessageEmojiModel(
        type = type.toMessageType(),
        eventList = eventList,
        messageId = messageId,
        emojiId = emojiId
    )