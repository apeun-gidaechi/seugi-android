package com.seugi.workspacedetail.feature.invitemember.model

import com.seugi.data.workspace.model.RetrieveMemberModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class WaitMemberUiState (
    val student: ImmutableList<RoomMemberItem> = persistentListOf(),
    val teacher: ImmutableList<RoomMemberItem> = persistentListOf(),
    val checked: ImmutableList<Long> = persistentListOf(),
    val workspaceCode: String = ""
)

data class RoomMemberItem(
    val id: Long = 0,
    val name: String,
    val memberProfile: String? = null,
    val checked: Boolean = false,
)