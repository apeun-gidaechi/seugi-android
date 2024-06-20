package com.apeun.gidaechi.data.workspace.model

data class CheckWorkspaceModel(
    val workspaceId: String,
    val workspaceName: String,
    val workspaceImageUrl: String,
    val studentCount: Int,
    val teacherCount: Int
)
