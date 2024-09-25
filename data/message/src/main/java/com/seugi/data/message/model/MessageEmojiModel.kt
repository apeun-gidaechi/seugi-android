package com.seugi.data.message.model

import kotlinx.collections.immutable.ImmutableList

data class MessageEmojiModel(
    val emojiId: Int,
    val userId: ImmutableList<Int>,
)
