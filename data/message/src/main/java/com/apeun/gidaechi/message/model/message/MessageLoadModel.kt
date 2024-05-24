package com.apeun.gidaechi.message.model.message

data class MessageLoadModel(
    val firstMessageId: String?,
    val messages: List<MessageMessageModel>
)