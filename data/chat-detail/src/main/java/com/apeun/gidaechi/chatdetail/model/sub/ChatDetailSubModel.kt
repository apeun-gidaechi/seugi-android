package com.apeun.gidaechi.chatdetail.model.sub

import com.apeun.gidaechi.chatdetail.model.ChatDetailTypeModel
import com.apeun.gidaechi.chatdetail.model.ChatType

data class ChatDetailSubModel(
    override val type: ChatType,
    val eventList: List<Int>
): ChatDetailTypeModel()