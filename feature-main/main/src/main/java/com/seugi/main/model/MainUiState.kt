package com.seugi.main.model

import android.provider.ContactsContract.Profile
import com.seugi.data.core.model.ProfileModel
import com.seugi.data.core.model.UserModel
import com.seugi.data.workspace.model.WorkspacePermissionModel

data class MainUiState(
    val workspaceId: String = "",
    val userId: Int = -1,
    val permission: WorkspacePermissionModel = WorkspacePermissionModel.STUDENT,
    val myProfile: ProfileModel = ProfileModel(
        status = "",
        member = UserModel(
            id = 0,
            email = "",
            birth = "",
            name = "",
            picture = ""
        ),
        workspaceId = "",
        nick = "",
        spot = "",
        belong = "",
        phone = "",
        wire = "",
        location = ""
    )
)
