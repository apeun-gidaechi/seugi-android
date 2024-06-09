package com.apeun.gidaechi.network.message.response.message

data class MessageLoadResponse(
    val firstMessageId: String?,
    val messages: List<MessageMessageResponse>,
)
