package com.apeun.gidaechi.network.workspace.response

import kotlinx.serialization.Serializable

@Serializable
data class CheckWorkspaceResponse(
    val workspaceId: String,
    val workspaceName: String,
    val workspaceImageUrl: String,
    val studentCount: Int,
    val teacherCount: Int,
)
