package com.apeun.gidaechi.message.model.sub

import com.apeun.gidaechi.message.model.MessageTypeModel
import com.apeun.gidaechi.message.model.MessageType

data class MessageSubModel(
    override val type: MessageType,
    val eventList: List<Int>
): MessageTypeModel()