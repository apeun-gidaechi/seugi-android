package com.seugi.workspacedetail.feature.workspacedetail.model

import com.seugi.data.workspace.model.WaitWorkspaceModel
import com.seugi.data.workspace.model.WorkspaceModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class WorkspaceDetailUiState(
    val loading: Boolean = false,
    val nowWorkspace: WorkspaceModel = WorkspaceModel(),
    val myWorkspace: ImmutableList<WorkspaceModel?> = listOf(WorkspaceModel()).toImmutableList(),
    val waitWorkspace: ImmutableList<WaitWorkspaceModel?> = listOf(WaitWorkspaceModel()).toImmutableList(),
)

sealed interface WorkspaceDetailSideEffect {
    data class Error(val throwable: Throwable) : WorkspaceDetailSideEffect
}
