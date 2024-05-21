package com.apeun.gidaechi.chatdetail.model.message

data class ChatDetailMessageLoadModel(
    val firstMessageId: String?,
    val messages: List<ChatDetailMessageModel>
)