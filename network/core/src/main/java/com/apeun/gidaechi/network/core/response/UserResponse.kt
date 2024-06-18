package com.apeun.gidaechi.network.core.response

data class UserResponse(
    val id: Int,
    val email: String,
    val birth: String,
    val name: String,
    val picture: String,
)
