package com.seugi.data.workspace.model

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList

@Stable
data class WorkspaceModel(
    val workspaceId: String,
    val workspaceName: String,
    val workspaceImageUrl: String,
    val workspaceAdmin: Long,
    val middleAdmin: ImmutableList<Long>,
    val teacher: ImmutableList<Long>,
    val student: ImmutableList<Long>,
)
