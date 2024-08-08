package com.seugi.data.workspace.mapper

import com.seugi.data.workspace.model.WorkspaceModel
import com.seugi.network.workspace.response.WorkspaceResponse


fun List<WorkspaceResponse>.toModels() = this.map {
    it.toModel()
}

fun WorkspaceResponse.toModel() = WorkspaceModel(
    workspaceId = workspaceId,
    workspaceName = workspaceName,
    workspaceImageUrl = workspaceImageUrl,
    workspaceAdmin = workspaceAdmin,
    middleAdmin = middleAdmin,
    teacher = teacher,
    student = student
)