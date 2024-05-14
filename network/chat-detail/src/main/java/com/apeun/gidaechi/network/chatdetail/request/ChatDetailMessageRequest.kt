package com.apeun.gidaechi.network.chatdetail.request

data class ChatDetailMessageRequest(
    val roomId: Int,
    val type: String,
    val message: String,
    val mention: List<Long>,
    val mentionAll: Boolean
) {
    constructor(roomId: Int, message: String): this(roomId, "MESSAGE", message, emptyList(), false)
}