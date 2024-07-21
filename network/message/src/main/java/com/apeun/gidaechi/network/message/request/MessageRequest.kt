package com.seugi.network.message.request

data class MessageRequest(
    val roomId: String,
    val type: String,
    val message: String,
    val mention: List<Long>,
    val mentionAll: Boolean,
) {
    constructor(roomId: String, message: String) : this(roomId, "MESSAGE", message, emptyList(), false)
}
