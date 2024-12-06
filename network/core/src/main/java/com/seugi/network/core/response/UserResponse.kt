package com.seugi.network.core.response

data class UserResponse(
    val id: Long,
    val email: String,
    val birth: String,
    val name: String,
    val picture: String,
)
