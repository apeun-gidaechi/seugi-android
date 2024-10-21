package com.seugi.workspacedetail.feature.invitemember.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class WaitMemberUiState (
    val student: ImmutableList<RoomMemberItem> = persistentListOf(),
    val teacher: ImmutableList<RoomMemberItem> = persistentListOf(),
    val workspaceCode: String = "",
    val waitMemberSize: Int = 0
)

data class RoomMemberItem(
    val id: Long = 0,
    val name: String,
    val memberProfile: String? = null,
    val checked: Boolean = false,
)