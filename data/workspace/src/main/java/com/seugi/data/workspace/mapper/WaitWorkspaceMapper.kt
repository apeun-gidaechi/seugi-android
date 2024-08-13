package com.seugi.data.workspace.mapper

import com.seugi.data.workspace.model.WaitWorkspaceModel
import com.seugi.network.workspace.response.WaitWorkspaceResponse


fun List<WaitWorkspaceResponse>.toModels() = map {
    it.toModel()
}


fun WaitWorkspaceResponse.toModel() = WaitWorkspaceModel(
    workspaceId = workspaceId,
    workspaceName = workspaceName,
    workspaceImageUrl = workspaceImageUrl,
    studentCount = studentCount,
    teacherCount = teacherCount
)