package com.seugi.data.workspace.mapper

import com.seugi.data.workspace.model.WorkspaceModel
import com.seugi.local.room.model.WorkspaceEntity
import com.seugi.network.workspace.response.WorkspaceResponse
import kotlinx.collections.immutable.toImmutableList

fun List<WorkspaceResponse>.toModels() = this.map {
    it.toModel()
}

fun WorkspaceResponse.toModel() = WorkspaceModel(
    workspaceId = workspaceId,
    workspaceName = workspaceName,
    workspaceImageUrl = workspaceImageUrl,
    workspaceAdmin = workspaceAdmin,
    middleAdmin = middleAdmin.toImmutableList(),
    teacher = teacher.toImmutableList(),
    student = student.toImmutableList(),
)

fun List<WorkspaceEntity>.localToModels() = this.map {
    it.localToModel()
}

fun WorkspaceEntity.localToModel() = WorkspaceModel(
    workspaceId = workspaceId,
    workspaceName = workspaceName,
    workspaceImageUrl = "",
    workspaceAdmin = 0,
    middleAdmin = listOf<Long>(0).toImmutableList(),
    student = listOf<Long>(0).toImmutableList(),
    teacher = listOf<Long>(0).toImmutableList(),
)

fun List<WorkspaceModel>.toEntities() = this.map {
    it.toEntity()
}

fun WorkspaceModel.toEntity() = WorkspaceEntity(
    workspaceId = workspaceId,
    workspaceName = workspaceName,
)
