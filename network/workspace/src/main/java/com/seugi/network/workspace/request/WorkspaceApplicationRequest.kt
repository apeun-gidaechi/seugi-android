package com.seugi.network.workspace.request

data class WorkspaceApplicationRequest(
    val workspaceId: String,
    val workspaceCode: String,
    val role: String,
)
