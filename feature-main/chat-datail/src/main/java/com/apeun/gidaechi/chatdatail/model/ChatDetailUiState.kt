package com.apeun.gidaechi.chatdatail.model

import java.time.LocalDateTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ChatUiState(
    val roomInfo: ChatRoomState? = null,
    val userInfo: TestUserModel? = null,
    val message: ImmutableList<TestMessageModel> = persistentListOf(),
)

data class ChatRoomState(
    val id: Int,
    val roomName: String,
    val members: ImmutableList<TestUserModel> = persistentListOf(),
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
    val userProfile: String? = null,
)
