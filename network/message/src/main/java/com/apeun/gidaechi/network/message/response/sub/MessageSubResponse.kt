package com.apeun.gidaechi.network.message.response.sub

import com.apeun.gidaechi.network.message.response.MessageTypeResponse

data class MessageSubResponse(
    override val type: String,
    val userId: Int,
    val eventList: List<Int>,
) : MessageTypeResponse()
