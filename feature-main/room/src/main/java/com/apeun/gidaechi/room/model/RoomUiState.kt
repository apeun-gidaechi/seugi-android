package com.apeun.gidaechi.room.model

import com.apeun.gidaechi.data.core.model.ChatRoomModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class RoomUiState(
    val chatItems: ImmutableList<ChatRoomModel> = persistentListOf(),
)

data class TestRoomItem(
    val chatId: Int = 0,
    val userName: String,
    val userProfile: String?,
    val message: String,
    val createdAt: String,
    val count: Int?,
    val memberCount: Int?,
)
