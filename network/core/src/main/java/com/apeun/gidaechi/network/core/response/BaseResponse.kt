package com.apeun.gidaechi.network.core.response

data class BaseResponse<T>(
    val data: T,
    val message: String,
    val status: Int,
    val state: String,
    val success: Boolean,
)
