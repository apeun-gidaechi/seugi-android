package com.apeun.gidaechi.chatdetail.model.message

import com.apeun.gidaechi.chatdetail.model.ChatDetailTypeModel
import com.apeun.gidaechi.chatdetail.model.ChatType

data class ChatDetailMessageDeleteModel(
    override val type: ChatType,
    val eventList: List<Int>,
    val messageId: String
): ChatDetailTypeModel()