package com.seugi.network.oauth.request

data class OauthRequest(
    val code: String,
    val platform: String,
    val token: String
)