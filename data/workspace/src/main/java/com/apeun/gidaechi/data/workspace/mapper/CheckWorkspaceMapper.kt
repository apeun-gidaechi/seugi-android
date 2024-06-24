package com.apeun.gidaechi.data.workspace.mapper

import com.apeun.gidaechi.data.workspace.model.CheckWorkspaceModel
import com.apeun.gidaechi.network.workspace.response.CheckWorkspaceResponse

fun CheckWorkspaceResponse.toModel() = CheckWorkspaceModel(
    workspaceId = workspaceId,
    workspaceName = workspaceName,
    workspaceImageUrl = workspaceImageUrl,
    studentCount = studentCount,
    teacherCount = teacherCount,
)
