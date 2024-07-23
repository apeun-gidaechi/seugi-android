package com.seugi.data.message.mapper

import com.seugi.data.message.model.emoji.MessageEmojiModel
import com.seugi.network.message.response.emoji.MessageEmojiResponse

internal fun MessageEmojiResponse.toModel() = MessageEmojiModel(
    type = type.toMessageType(),
    eventList = eventList,
    messageId = messageId,
    emojiId = emojiId,
)
