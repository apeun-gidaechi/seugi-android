package com.seugi.data.core.model

data class ProfileModel(
    val status: String,
    val member: UserModel,
    val workspaceId: String,
    val nick: String,
    val spot: String,
    val belong: String,
    val phone: String,
    val wire: String,
    val location: String,
)
