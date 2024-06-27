package com.apeun.gidaechi.profile.model

import com.apeun.gidaechi.data.core.model.ProfileModel
import com.apeun.gidaechi.data.core.model.UserModel

data class ProfileUiState(
    val profileInfo: ProfileModel = ProfileModel(
        "",
        UserModel(0, "", "", "", ""),
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    )
)