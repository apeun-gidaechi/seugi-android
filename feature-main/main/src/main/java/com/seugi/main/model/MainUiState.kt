package com.seugi.main.model

import com.seugi.data.workspace.model.WorkspacePermissionModel

data class MainUiState(
    val workspaceId: String = "669e339593e10f4f59f8c583",
    val userId: Int = -1,
    val permission: WorkspacePermissionModel = WorkspacePermissionModel.STUDENT,
)
