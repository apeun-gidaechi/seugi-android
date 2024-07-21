package com.seugi.data.token.mapper

import com.seugi.data.token.model.TokenModel
import com.seugi.local.room.model.TokenEntity

fun TokenEntity?.toModel() = TokenModel(
    accessToken = this?.token,
    refreshToken = this?.refreshToken,
)
