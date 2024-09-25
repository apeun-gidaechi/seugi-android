package com.seugi.network.message.response.message

import com.seugi.network.message.response.MessageRoomEventResponse

data class MessageLoadResponse(
    val firstMessageId: String?,
    val messages: List<MessageRoomEventResponse.MessageParent.Message>,
)
