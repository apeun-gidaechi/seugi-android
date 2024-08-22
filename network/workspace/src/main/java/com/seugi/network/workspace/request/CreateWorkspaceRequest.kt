package com.seugi.network.workspace.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateWorkspaceRequest(
    val workspaceName: String,
    val workspaceImageUrl: String,
)
