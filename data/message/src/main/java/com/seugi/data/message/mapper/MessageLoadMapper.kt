package com.seugi.data.message.mapper

import com.seugi.data.message.model.MessageLoadModel
import com.seugi.network.message.response.message.MessageLoadResponse
import kotlinx.collections.immutable.toImmutableList

fun MessageLoadResponse.toModel(userId: Long) = MessageLoadModel(
    firstMessageId = firstMessageId,
    messages = messages.map { it.toModel(userId) }.toImmutableList(),
)
