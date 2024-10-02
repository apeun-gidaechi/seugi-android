package com.seugi.data.core.model

data class TokenModel(
    val accessToken: String,
    val refreshToken: String,
)

data class LocalTokenModel(
    val accessToken: String?,
    val refreshToken: String?,
)
