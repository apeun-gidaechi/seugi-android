package com.apeun.gidaechi.message.model.sub

import com.apeun.gidaechi.message.model.MessageType
import com.apeun.gidaechi.message.model.MessageTypeModel
import kotlinx.collections.immutable.ImmutableList

data class MessageSubModel(
    override val type: MessageType,
    val eventList: ImmutableList<Int>,
) : MessageTypeModel()
