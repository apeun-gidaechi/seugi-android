package com.seugi.data.message.mapper

import com.seugi.network.message.response.sub.MessageSubResponse
import com.seugi.data.message.model.sub.MessageSubModel
import kotlinx.collections.immutable.toImmutableList

internal fun MessageSubResponse.toModel() = MessageSubModel(
    type = type.toMessageType(),
    userId = userId,
    eventList = eventList.toImmutableList(),
)
