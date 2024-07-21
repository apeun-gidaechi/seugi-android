package com.seugi.data.message.model.message

import kotlinx.collections.immutable.ImmutableList

data class MessageLoadModel(
    val firstMessageId: String?,
    val messages: ImmutableList<MessageMessageModel>,
)
