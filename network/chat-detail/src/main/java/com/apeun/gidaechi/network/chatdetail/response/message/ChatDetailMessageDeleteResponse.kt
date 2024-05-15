package com.apeun.gidaechi.network.chatdetail.response.message

import com.apeun.gidaechi.network.chatdetail.response.ChatDetailTypeResponse

data class ChatDetailMessageDeleteResponse(
    override val type: String,
    val eventList: List<Int>,
    val messageId: String
): ChatDetailTypeResponse()