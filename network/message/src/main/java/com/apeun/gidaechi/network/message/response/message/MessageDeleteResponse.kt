package com.apeun.gidaechi.network.message.response.message

import com.apeun.gidaechi.network.message.response.MessageTypeResponse

data class MessageDeleteResponse(
    override val type: String,
    val eventList: List<Int>,
    val messageId: String
): MessageTypeResponse()