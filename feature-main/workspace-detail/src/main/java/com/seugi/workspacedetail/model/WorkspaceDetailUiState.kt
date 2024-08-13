package com.seugi.workspacedetail.model

import com.seugi.data.workspace.model.WaitWorkspaceModel
import com.seugi.data.workspace.model.WorkspaceModel
import kotlinx.collections.immutable.ImmutableList

data class WorkspaceDetailUiState(
    val myWorkspace: ImmutableList<WorkspaceModel?>? = null,
    val waitWorkspace: ImmutableList<WaitWorkspaceModel?>? = null
)