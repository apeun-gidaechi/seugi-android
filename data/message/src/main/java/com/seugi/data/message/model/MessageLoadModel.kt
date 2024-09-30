package com.seugi.data.message.model

import kotlinx.collections.immutable.ImmutableList

data class MessageLoadModel(
    val firstMessageId: String?,
    val messages: ImmutableList<MessageRoomEvent.MessageParent>,
)
