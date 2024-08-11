package com.seugi.data.workspace.model

data class WorkspaceModel(
    val workspaceId: String,
    val workspaceName: String,
    val workspaceImageUrl: String,
    val workspaceAdmin: Long,
    val middleAdmin: List<Long>,
    val teacher: List<Long>,
    val student: List<Long>,
)
