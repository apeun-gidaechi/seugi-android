package com.seugi.main.model

import com.seugi.data.core.model.ProfileModel
import com.seugi.data.core.model.UserModel
import com.seugi.data.core.model.WorkspacePermissionModel

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
)
