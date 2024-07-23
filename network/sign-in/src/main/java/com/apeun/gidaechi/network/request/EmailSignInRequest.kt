package com.seugi.network.request

import kotlinx.serialization.Serializable
@Serializable
data class EmailSignInRequest(
    val email: String,
    val password: String,
)
