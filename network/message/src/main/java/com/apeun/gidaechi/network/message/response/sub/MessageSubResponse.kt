package com.seugi.network.message.response.sub

import com.seugi.network.message.response.MessageTypeResponse

data class MessageSubResponse(
    override val type: String,
    val userId: Int,
    val eventList: List<Int>,
) : MessageTypeResponse()
