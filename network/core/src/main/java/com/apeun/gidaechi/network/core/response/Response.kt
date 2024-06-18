package com.apeun.gidaechi.network.core.response

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val message: String,
    val status: Int,
    val state: String,
    val success: Boolean
)
