package com.apeun.gidaechi.chatdatail.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDateTime

data class ChatUiState(
    val roomInfo: ChatRoomState? = null,
    val userInfo: TestUserModel? = null,
    val message: ImmutableList<TestMessageModel> = persistentListOf()
)

data class ChatRoomState(
    val id: Int,
    val roomName: String
)

data class TestMessageModel(
    val id: Int,
    val userName: String,
    val userId: Int,
    val message: String,
    val createdAt: LocalDateTime,
)

data class TestUserModel(
    val id: Int,
    val userName: String,
)