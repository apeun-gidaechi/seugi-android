package com.apeun.gidaechi.message.model.message

import kotlinx.collections.immutable.ImmutableList

data class MessageLoadModel(
    val firstMessageId: String?,
    val messages: ImmutableList<MessageMessageModel>,
)
