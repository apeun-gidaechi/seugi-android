package com.seugi.network.workspace.request

import kotlinx.serialization.Serializable


@Serializable
data class AddMemberRequest(
    val workspaceId: String,
    val userSet: List<Long>,
    val role: String
)
