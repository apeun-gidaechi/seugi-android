package com.apeun.gidaechi.chat.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ChatUiState(
    val chatItems: ImmutableList<TestChatItem> = persistentListOf(),
)

data class TestChatItem(
    val chatId: Int = 0,
    val userName: String,
    val userProfile: String?,
    val message: String,
    val createdAt: String,
    val count: Int?,
)
