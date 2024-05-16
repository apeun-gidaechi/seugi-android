package com.apeun.gidaechi.network.response

import kotlinx.serialization.Serializable

@Serializable
data class EmailSignInResponse(
    val accessToken: String,
    val refreshToken: String
)
