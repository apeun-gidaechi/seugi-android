package com.apeun.gidaechi.chatdetail.mapper

import com.apeun.gidaechi.chatdetail.model.profile.ChatProfileModel
import com.apeun.gidaechi.network.chatdetail.response.profile.ChatDetailProfileResponse

internal fun ChatDetailProfileResponse.toModel() =
    ChatProfileModel(
        status = status,
        nick = nick,
        spot = spot,
        belong = belong,
        phone = phone,
        wire = wire,
        location = location
    )