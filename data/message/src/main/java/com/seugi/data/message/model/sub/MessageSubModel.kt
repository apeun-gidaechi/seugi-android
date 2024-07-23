package com.seugi.data.message.model.sub

import com.seugi.data.message.model.MessageType
import com.seugi.data.message.model.MessageTypeModel
import kotlinx.collections.immutable.ImmutableList

data class MessageSubModel(
    override val type: MessageType,
    val userId: Int,
    val eventList: ImmutableList<Int>,
) : MessageTypeModel()
