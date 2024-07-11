package com.apeun.gidaechi.chatseugi.model

import java.time.LocalDateTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ChatSeugiUiState(
    val chatMessage: ImmutableList<ChatData> = persistentListOf(),
)

sealed interface ChatData {
    data class AI(val message: String, val timestamp: LocalDateTime, val isFirst: Boolean, val isLast: Boolean) : ChatData
    data class User(val message: String, val timestamp: LocalDateTime, val isLast: Boolean) : ChatData
}
