package com.seugi.data.oauth

import com.seugi.network.core.response.TokenResponse

data class TokenModel(
    val accessToken: String,
    val refreshToken: String,
)


fun TokenResponse.toModel(): TokenModel = TokenModel(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)