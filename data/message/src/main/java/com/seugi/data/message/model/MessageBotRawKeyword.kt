package com.seugi.data.message.model

data class MessageBotRawKeyword(
    val keyword: String
)

data class MessageBotRawKeywordInData <T> (
    val keyword: String,
    val data: T
)