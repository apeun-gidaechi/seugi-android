package com.apeun.gidaechi.message.mapper

import com.apeun.gidaechi.message.model.sub.MessageSubModel
import com.apeun.gidaechi.network.chatdetail.response.sub.ChatDetailSubResponse


internal fun ChatDetailSubResponse.toModel() =
    MessageSubModel(
        type = type.toMessageType(),
        eventList = eventList,
    )