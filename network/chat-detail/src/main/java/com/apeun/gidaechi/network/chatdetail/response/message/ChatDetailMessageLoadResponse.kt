package com.apeun.gidaechi.network.chatdetail.response.message

data class ChatDetailMessageLoadResponse(
    val firstMessageId: String?,
    val messages: List<ChatDetailMessageResponse>
)