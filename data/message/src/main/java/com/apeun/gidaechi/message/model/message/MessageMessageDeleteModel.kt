package com.apeun.gidaechi.message.model.message

import com.apeun.gidaechi.message.model.MessageType
import com.apeun.gidaechi.message.model.MessageTypeModel

data class MessageMessageDeleteModel(
    override val type: MessageType,
    val eventList: List<Int>,
    val messageId: String,
) : MessageTypeModel()
