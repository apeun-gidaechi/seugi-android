package com.seugi.main.model

import com.seugi.data.core.model.ProfileModel
import com.seugi.data.core.model.UserModel
import com.seugi.data.core.model.WorkspacePermissionModel
import com.seugi.data.workspace.model.WorkspaceModel

data class MainUiState(
    val userId: Int = -1,
    val profile: ProfileModel = ProfileModel(
        status = "",
        member = UserModel(
            id = 0,
            email = "",
            birth = "",
            name = "",
            picture = "",
        ),
        workspaceId = "",
        nick = "",
        spot = "",
        belong = "",
        phone = "",
        wire = "",
        location = "",
        permission = WorkspacePermissionModel.STUDENT,
        schGrade = 0,
        schClass = 0,
        schNumber = 0,
    ),
    val workspace: WorkspaceModel = WorkspaceModel(
        workspaceId = "",
        workspaceName = "",
        workspaceAdmin = 0,
        workspaceImageUrl = ""
    ),
    val errorLoadWorkspace: Boolean = false
)
