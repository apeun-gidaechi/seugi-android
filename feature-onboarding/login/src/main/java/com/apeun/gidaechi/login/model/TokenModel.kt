package com.apeun.gidaechi.login.model

data class TokenModel(
    val accessToken: String = "",
    val refreshToken: String = ""
)
