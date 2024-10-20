package com.seugi.network.workspace.request

import kotlinx.serialization.Serializable


@Serializable
data class InviteMemberRequest(
    val workspaceId: String,
    val userSet: List<Long>,
    val role: String
)
