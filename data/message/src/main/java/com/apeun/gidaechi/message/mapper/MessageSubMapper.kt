package com.apeun.gidaechi.message.mapper

import com.apeun.gidaechi.message.model.sub.MessageSubModel
import com.apeun.gidaechi.network.message.response.sub.MessageSubResponse


internal fun MessageSubResponse.toModel() =
    MessageSubModel(
        type = type.toMessageType(),
        eventList = eventList,
    )