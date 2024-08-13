package com.seugi.data.workspace.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class WorkspaceModel(
    val workspaceId: String = "",
    val workspaceName: String = "",
    val workspaceImageUrl: String = "",
    val workspaceAdmin: Long = 0,
    val middleAdmin: ImmutableList<Long> = listOf<Long>(0).toImmutableList(),
    val teacher: ImmutableList<Long> = listOf<Long>(0).toImmutableList(),
    val student: ImmutableList<Long> = listOf<Long>(0).toImmutableList(),
)
