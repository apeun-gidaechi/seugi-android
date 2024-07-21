package com.seugi.profile.model

import com.seugi.data.core.model.ProfileModel
import com.seugi.data.core.model.UserModel

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
        "",
    ),
)
