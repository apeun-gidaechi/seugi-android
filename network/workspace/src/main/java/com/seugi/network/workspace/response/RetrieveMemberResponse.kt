package com.seugi.network.workspace.response

import kotlinx.serialization.Serializable

@Serializable
data class RetrieveMemberResponse(
    val id: Long,
    val email: String,
    val birth: String,
    val name: String,
    val picture: String,
)
