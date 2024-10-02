package com.seugi.data.core.mapper

import com.seugi.data.core.model.LocalTokenModel
import com.seugi.data.core.model.TokenModel
import com.seugi.local.room.model.TokenEntity
import com.seugi.network.core.response.TokenResponse


fun TokenResponse.toModel(): TokenModel = TokenModel(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)

fun TokenEntity?.toModel() = LocalTokenModel(
    accessToken = this?.token,
    refreshToken = this?.refreshToken,
)
