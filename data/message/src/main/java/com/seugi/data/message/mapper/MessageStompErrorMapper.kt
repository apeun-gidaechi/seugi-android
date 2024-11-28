package com.seugi.data.message.mapper

import com.seugi.data.message.model.MessageStompErrorModel
import com.seugi.network.message.response.stomp.MessageStompErrorResponse

internal fun MessageStompErrorResponse.toModel() = MessageStompErrorModel(
    status = status,
    success = success,
    state = state,
    message = message,
)
