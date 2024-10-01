package com.seugi.network.core.response

import kotlinx.serialization.Serializable


@Serializable
data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)
