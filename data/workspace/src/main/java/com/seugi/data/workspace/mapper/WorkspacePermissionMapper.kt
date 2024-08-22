package com.seugi.data.workspace.mapper

import com.seugi.data.workspace.model.WorkspacePermissionModel
import com.seugi.network.workspace.response.WorkspacePermissionResponse

fun WorkspacePermissionResponse.toModel() =
    when (this) {
        WorkspacePermissionResponse.ADMIN -> WorkspacePermissionModel.ADMIN
        WorkspacePermissionResponse.STUDENT -> WorkspacePermissionModel.STUDENT
        WorkspacePermissionResponse.TEACHER -> WorkspacePermissionModel.TEACHER
        WorkspacePermissionResponse.MIDDLE_ADMIN -> WorkspacePermissionModel.MIDDLE_ADMIN
    }