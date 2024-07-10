package com.apeun.gidaechi.data.token.mapper

import com.apeun.gidaechi.data.token.model.TokenModel
import com.apeun.gidaechi.local.room.model.TokenEntity

fun TokenEntity?.toModel() =
    TokenModel(
        accessToken = this?.token,
        refreshToken = this?.refreshToken
    )