package com.seugi.network.core.response

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val data: T,
    val message: String,
    val status: Int,
    val state: String,
    val success: Boolean,
)
