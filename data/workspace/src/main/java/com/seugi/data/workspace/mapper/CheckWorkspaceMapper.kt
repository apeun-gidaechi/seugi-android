package com.seugi.data.workspace.mapper

import com.seugi.data.workspace.model.CheckWorkspaceModel
import com.seugi.network.workspace.response.CheckWorkspaceResponse

fun CheckWorkspaceResponse.toModel() = CheckWorkspaceModel(
    workspaceId = workspaceId,
    workspaceName = workspaceName,
    workspaceImageUrl = workspaceImageUrl,
    studentCount = studentCount,
    teacherCount = teacherCount,
)
