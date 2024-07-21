package com.seugi.roomcreate.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class RoomCreateUiState(
    val userItem: ImmutableList<RoomMemberItem> = persistentListOf(),
) {
    val checkedMemberState: ImmutableList<RoomMemberItem> by lazy {
        userItem.filter { it.checked }.toImmutableList()
    }
}

data class RoomMemberItem(
    val id: Int = 0,
    val name: String,
    val memberProfile: String? = null,
    val checked: Boolean = false,
)

sealed interface RoomCreateSideEffect {
    data class SuccessCreateRoom(val chatRoomUid: String, val isPersonal: Boolean) :
        RoomCreateSideEffect
}
