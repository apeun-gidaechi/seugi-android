package com.seugi.workspacedetail.feature.invitemember.model

import com.seugi.data.workspace.model.RetrieveMemberModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class WaitMemberUiState (
    val student: ImmutableList<RetrieveMemberModel> = persistentListOf(),
    val teacher: ImmutableList<RetrieveMemberModel> = persistentListOf()
)