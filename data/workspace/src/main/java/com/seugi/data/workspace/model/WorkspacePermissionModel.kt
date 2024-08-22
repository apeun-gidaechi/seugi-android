package com.seugi.data.workspace.model

enum class WorkspacePermissionModel {
    ADMIN,
    MIDDLE_ADMIN,
    TEACHER,
    STUDENT,
}

fun WorkspacePermissionModel.isAdmin() =
    when (this) {
        WorkspacePermissionModel.ADMIN -> true
        WorkspacePermissionModel.MIDDLE_ADMIN -> true
        else -> false
    }