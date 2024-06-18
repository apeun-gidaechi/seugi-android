package com.apeun.gidaechi.room.model

import com.apeun.gidaechi.data.core.model.ChatRoomModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class RoomUiState(
    val chatItems: ImmutableList<ChatRoomModel> = persistentListOf(),
)
