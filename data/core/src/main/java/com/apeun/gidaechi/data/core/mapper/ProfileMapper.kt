package com.apeun.gidaechi.data.core.mapper

import com.apeun.gidaechi.data.core.model.ProfileModel
import com.apeun.gidaechi.network.core.response.ProfileResponse

fun List<ProfileResponse>.toModels() = this.map {
    it.toModel()
}

fun ProfileResponse.toModel() = ProfileModel(
    status = status,
    member = member.toModel(),
    workspaceId = workspaceId,
    nick = nick,
    spot = spot,
    belong = belong,
    phone = phone,
    wire = wire,
    location = location,
)
