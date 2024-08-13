package com.seugi.network.workspace.response

import kotlinx.serialization.Serializable

@Serializable
data class WaitWorkspaceResponse(
    val workspaceId: String,
    val workspaceName: String,
    val workspaceImageUrl: String,
    val studentCount: String,
    val teacherCount: String
)
