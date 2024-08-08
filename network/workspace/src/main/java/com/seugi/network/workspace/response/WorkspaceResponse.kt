package com.seugi.network.workspace.response

import kotlinx.serialization.Serializable

@Serializable
data class WorkspaceResponse(
    val workspaceId: String,
    val workspaceName: String,
    val workspaceImageUrl: String,
    val workspaceAdmin: Long,
    val middleAdmin: List<Long>,
    val teacher: List<Long>,
    val student: List<Long>,

)