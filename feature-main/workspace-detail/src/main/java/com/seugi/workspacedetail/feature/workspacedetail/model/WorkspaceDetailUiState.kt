package com.seugi.workspacedetail.feature.workspacedetail.model

import com.seugi.data.workspace.model.WaitWorkspaceModel
import com.seugi.data.workspace.model.WorkspaceModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class WorkspaceDetailUiState(
    val loading: Boolean = false,
    val nowWorkspace: WorkspaceModel = WorkspaceModel(),
    val myWorkspace: ImmutableList<WorkspaceModel?> = persistentListOf(),
    val waitWorkspace: ImmutableList<WaitWorkspaceModel?> = persistentListOf(),
)

sealed interface WorkspaceDetailSideEffect {
    data class Error(val throwable: Throwable) : WorkspaceDetailSideEffect
}
