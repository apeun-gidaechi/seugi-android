package com.apeun.gidaechi.message.model.message

import com.apeun.gidaechi.message.model.MessageType
import com.apeun.gidaechi.message.model.MessageTypeModel
import kotlinx.collections.immutable.ImmutableList

data class MessageMessageDeleteModel(
    override val type: MessageType,
    val eventList: ImmutableList<Int>,
    val messageId: String,
) : MessageTypeModel()
