package com.seugi.workspacedetail.model

import com.seugi.data.workspace.model.WorkspaceModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class WorkspaceDetailUiState(
    val myWorkspace: ImmutableList<WorkspaceModel?>? = null,
    val waitWorkspace: ImmutableList<WorkspaceModel?>? = null
)