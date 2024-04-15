package com.apeun.gidaechi.roomcreate.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class RoomCreateUiState(
    val userItem: ImmutableList<TestMemberItem> = persistentListOf()
) {
    val checkedMemberState: ImmutableList<TestMemberItem> by lazy {
        userItem.filter { it.checked }.toImmutableList()
    }
}

data class TestMemberItem(
    val id: Int = 0,
    val name: String,
    val memberProfile: String? = null,
    val checked: Boolean = false
)
