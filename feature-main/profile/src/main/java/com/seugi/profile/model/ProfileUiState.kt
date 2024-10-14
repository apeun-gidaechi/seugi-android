package com.seugi.profile.model

import com.seugi.data.core.model.ProfileModel
import com.seugi.data.core.model.UserModel
import com.seugi.data.core.model.WorkspacePermissionModel

data class ProfileUiState(
    val profileInfo: ProfileModel = ProfileModel(
        status = "",
        UserModel(0, "", "", "", ""),
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
