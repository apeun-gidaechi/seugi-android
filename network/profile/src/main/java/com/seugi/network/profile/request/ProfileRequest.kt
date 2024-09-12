package com.seugi.network.profile.request

data class ProfileRequest(
    val nick: String,
    val status: String,
    val spot: String,
    val belong: String,
    val phone: String,
    val wire: String,
    val location: String,
)