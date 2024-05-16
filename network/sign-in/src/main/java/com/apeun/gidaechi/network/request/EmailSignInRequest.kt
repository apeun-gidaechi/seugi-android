package com.apeun.gidaechi.network.request

import kotlinx.serialization.Serializable
@Serializable
data class EmailSignInRequest(
    val email: String,
    val password: String
)
