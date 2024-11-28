package com.seugi.network.message.response.stomp

data class MessageStompErrorResponse(
    val status: Int,
    val success: Boolean,
    val state: String,
    val message: String,
)
