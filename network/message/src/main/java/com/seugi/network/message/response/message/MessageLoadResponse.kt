package com.seugi.network.message.response.message

data class MessageLoadResponse(
    val firstMessageId: String?,
    val messages: List<MessageMessageResponse>,
)
