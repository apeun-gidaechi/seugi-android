package com.apeun.gidaechi.data.profile.mapper

import com.apeun.gidaechi.data.profile.model.ProfileModel
import com.apeun.gidaechi.network.profile.response.ProfileResponse

internal fun ProfileResponse.toModel() = ProfileModel(
    status = status,
    memberId = memberId,
    nick = nick,
    spot = spot,
    belong = belong,
    phone = phone,
    wire = wire,
    location = location,
)
