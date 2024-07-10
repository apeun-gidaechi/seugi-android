package com.apeun.gidaechi.message.model.message

import kotlinx.collections.immutable.ImmutableList

data class MessageEmojiModel(
    val emojiId: Int,
    val userId: ImmutableList<Int>,
)
