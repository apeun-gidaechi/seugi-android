package com.seugi.workspacedetail.model

import com.seugi.data.workspace.model.WaitWorkspaceModel
import com.seugi.data.workspace.model.WorkspaceModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class WorkspaceDetailUiState(
    val nowWorkspace: Pair<String, String> = Pair("", ""),
    val myWorkspace: ImmutableList<WorkspaceModel?> = listOf(WorkspaceModel()).toImmutableList(),
    val waitWorkspace: ImmutableList<WaitWorkspaceModel?> = listOf(WaitWorkspaceModel()).toImmutableList()
)