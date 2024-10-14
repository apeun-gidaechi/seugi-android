package com.seugi.network.message.request

data class MessageRequest(
    val roomId: String,
    val type: String,
    val message: String,
    val mention: List<Long>,
    val mentionAll: Boolean,
    val emotion: String?,
    val uuid: String,
) {
    constructor(roomId: String, message: String, uuid: String, type: String) : this(roomId, type, message, emptyList(), false, null, uuid)
}
