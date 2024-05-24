package com.apeun.gidaechi.message.mapper

import com.apeun.gidaechi.message.model.emoji.MessageEmojiModel
import com.apeun.gidaechi.network.message.response.emoji.MessageEmojiResponse


internal fun MessageEmojiResponse.toModel() =
    MessageEmojiModel(
        type = type.toMessageType(),
        eventList = eventList,
        messageId = messageId,
        emojiId = emojiId
    )