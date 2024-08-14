package com.seugi.workspacedetail.feature.workspacemember.model

import com.seugi.data.core.model.ProfileModel
import com.seugi.data.core.model.UserModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class WorkspaceMemberUiState(
    val member: ImmutableList<ProfileModel>  = persistentListOf()
)