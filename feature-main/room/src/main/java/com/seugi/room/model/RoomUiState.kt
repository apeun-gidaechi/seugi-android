package com.seugi.room.model

import com.seugi.data.core.model.ChatRoomModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class RoomUiState(
    val isRefresh: Boolean = false,
    val filterMessage: String = "",
    private val _chatItems: ImmutableList<ChatRoomModel> = persistentListOf(),
) {
    val chatItems = _chatItems
        .filter {
            it.chatName.contains(filterMessage)
        }
        .toImmutableList()
}
