package com.apeun.gidaechi.chatdetail.mapper

import com.apeun.gidaechi.chatdetail.model.sub.ChatDetailSubModel
import com.apeun.gidaechi.network.chatdetail.response.sub.ChatDetailSubResponse


internal fun ChatDetailSubResponse.toModel() =
    ChatDetailSubModel(
        type = type.toChatType(),
        eventList = eventList,
    )