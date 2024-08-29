package com.seugi.main.model

import com.seugi.data.workspace.model.WorkspacePermissionModel

data class MainUiState(
    val workspaceId: String = "",
    val userId: Int = -1,
    val permission: WorkspacePermissionModel = WorkspacePermissionModel.STUDENT,
)
