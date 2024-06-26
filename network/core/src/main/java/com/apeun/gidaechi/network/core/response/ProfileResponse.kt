package com.apeun.gidaechi.network.core.response

data class ProfileResponse(
    val status: String,
    val workspaceId: String,
    val member: UserResponse,
    val nick: String,
    val spot: String,
    val belong: String,
    val phone: String,
    val wire: String,
    val location: String,
)
