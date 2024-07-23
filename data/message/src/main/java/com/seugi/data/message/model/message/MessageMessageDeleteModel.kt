package com.seugi.data.message.model.message

import com.seugi.data.message.model.MessageType
import com.seugi.data.message.model.MessageTypeModel
import kotlinx.collections.immutable.ImmutableList

data class MessageMessageDeleteModel(
    override val type: MessageType,
    val eventList: ImmutableList<Int>,
    val messageId: String,
) : MessageTypeModel()
