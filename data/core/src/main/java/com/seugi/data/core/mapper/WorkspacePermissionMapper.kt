package com.seugi.data.core.mapper

import com.seugi.data.core.model.WorkspacePermissionModel
import com.seugi.network.core.response.WorkspacePermissionResponse

fun WorkspacePermissionResponse.toModel() = when (this) {
    WorkspacePermissionResponse.ADMIN -> WorkspacePermissionModel.ADMIN
    WorkspacePermissionResponse.STUDENT -> WorkspacePermissionModel.STUDENT
    WorkspacePermissionResponse.TEACHER -> WorkspacePermissionModel.TEACHER
    WorkspacePermissionResponse.MIDDLE_ADMIN -> WorkspacePermissionModel.MIDDLE_ADMIN
}
