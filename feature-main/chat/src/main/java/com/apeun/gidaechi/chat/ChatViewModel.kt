package com.apeun.gidaechi.chat

import androidx.lifecycle.ViewModel
import com.apeun.gidaechi.chat.model.ChatUiState
import com.apeun.gidaechi.chat.model.TestChatItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ChatUiState())
    val state = _state.asStateFlow()

    fun loadChats() {
        _state.value = _state.value.copy(
            chatItems = listOf(
                TestChatItem(
                    0,
                    "노영재",
                    null,
                    "나.. 사실..",
                    "12:39",
                    if (Random.nextInt() == 0) null else Random.nextInt(),
                ),
                TestChatItem(
                    0,
                    "노영재",
                    null,
                    "나.. 사실..",
                    "12:39",
                    if (Random.nextInt() == 0) null else Random.nextInt(),
                ),
                TestChatItem(
                    0,
                    "노영재",
                    null,
                    "나.. 사실..",
                    "12:39",
                    if (Random.nextInt() == 0) null else Random.nextInt(),
                ),
                TestChatItem(
                    0,
                    "노영재",
                    null,
                    "나.. 사실..",
                    "12:39",
                    if (Random.nextInt() == 0) null else Random.nextInt(),
                ),
                TestChatItem(
                    0,
                    "노영재",
                    null,
                    "나.. 사실..",
                    "12:39",
                    if (Random.nextInt() == 0) null else Random.nextInt(),
                ),
                TestChatItem(
                    0,
                    "노영재",
                    null,
                    "나.. 사실..",
                    "12:39",
                    if (Random.nextInt() == 0) null else Random.nextInt(),
                ),
                TestChatItem(
                    0,
                    "노영재",
                    null,
                    "나.. 사실..",
                    "12:39",
                    if (Random.nextInt() == 0) null else Random.nextInt(),
                ),
                TestChatItem(
                    0,
                    "노영재",
                    null,
                    "나.. 사실..",
                    "12:39",
                    if (Random.nextInt() == 0) null else Random.nextInt(),
                ),
                TestChatItem(
                    0,
                    "노영재",
                    null,
                    "나.. 사실..",
                    "12:39",
                    if (Random.nextInt() == 0) null else Random.nextInt(),
                ),
                TestChatItem(
                    0,
                    "노영재",
                    null,
                    "나.. 사실..",
                    "12:39",
                    if (Random.nextInt() == 0) null else Random.nextInt(),
                ),
                TestChatItem(
                    0,
                    "노영재",
                    null,
                    "나.. 사실..",
                    "12:39",
                    if (Random.nextInt() == 0) null else Random.nextInt(),
                ),
            ).toImmutableList(),
        )
    }
}
