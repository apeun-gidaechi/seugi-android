package com.apeun.gidaechi.chat.model

import com.apeun.gidaechi.data.core.model.ChatRoomModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ChatUiState(
    val chatItems: ImmutableList<ChatRoomModel> = persistentListOf(),
)