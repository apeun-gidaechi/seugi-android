package com.seugi.network.message.request

data class MessageRequest(
    val roomId: String,
    val type: String,
    val message: String,
    val mention: List<Int>,
    val mentionAll: Boolean,
    val emotion: String?,
    val uuid: String,
) {
    constructor(roomId: String, message: String, uuid: String, type: String, mention: List<Int>) : this(roomId, type, message, mention, false, null, uuid)
}
