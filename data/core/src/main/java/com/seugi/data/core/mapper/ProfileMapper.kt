package com.seugi.data.core.mapper

import com.seugi.data.core.model.ProfileModel
import com.seugi.network.core.response.ProfileResponse

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
    permission = permission.toModel(),
    schGrade = schGrade,
    schClass = schClass,
    schNumber = schNumber
)
