package com.seugi.data.message.mapper

import com.seugi.data.message.model.MessageEmojiModel
import com.seugi.network.message.response.message.MessageEmojiResponse
import kotlinx.collections.immutable.toImmutableList

internal fun MessageEmojiResponse.toModel() = MessageEmojiModel(
    userId = userId.toImmutableList(),
    emojiId = emojiId,
)
