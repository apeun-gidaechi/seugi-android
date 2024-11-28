package com.seugi.data.message.model

data class MessageStompErrorModel(
    val status: Int,
    val success: Boolean,
    val state: String,
    val message: String,
)
