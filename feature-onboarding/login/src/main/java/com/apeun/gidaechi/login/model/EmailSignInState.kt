package com.apeun.gidaechi.login.model

data class EmailSignInState(
    val accessToken: String = "",
    val refreshToken: String = "",
)
