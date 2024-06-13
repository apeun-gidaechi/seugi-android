package com.apeun.gidaechi.network.request

import kotlinx.serialization.Serializable


@Serializable
data class EmailSignUpReqest (
    val name: String,
    val email: String,
    val password: String,
    val code: String
)