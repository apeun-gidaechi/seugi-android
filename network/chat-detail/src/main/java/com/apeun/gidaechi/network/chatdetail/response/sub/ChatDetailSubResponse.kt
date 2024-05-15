package com.apeun.gidaechi.network.chatdetail.response.sub

import com.apeun.gidaechi.network.chatdetail.response.ChatDetailTypeResponse

data class ChatDetailSubResponse(
    override val type: String,
    val eventList: List<Int>
): ChatDetailTypeResponse()