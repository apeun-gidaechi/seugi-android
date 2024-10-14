package com.seugi.login.model

data class EmailSignInState(
    val accessToken: String = "",
    val refreshToken: String = "",
)
