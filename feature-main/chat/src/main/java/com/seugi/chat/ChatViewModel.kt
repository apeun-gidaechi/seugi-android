package com.seugi.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.chat.model.ChatUiState
import com.seugi.common.model.Result
import com.seugi.data.personalchat.PersonalChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val personalChatRepository: PersonalChatRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ChatUiState())
    val state = _state.asStateFlow()

    fun loadChats(workspaceId: String) = viewModelScope.launch(Dispatchers.IO) {
        personalChatRepository.getAllChat(workspaceId).collect {
            when (it) {
                is Result.Success -> {
                    Log.d("TAG", "loadChats: ${it.data}")
                    _state.value = _state.value.copy(
                        _chatItems = it.data.sortedByDescending {
                            it.lastMessageTimestamp
                        }.toImmutableList(),
                    )
                }
                is Result.Error -> {
                    it.throwable.printStackTrace()
                }
                is Result.Loading -> {}
            }
        }

//        _state.value = _state.value.copy(
//            chatItems = listOf(
//                TestChatItem(
//                    0,
//                    "노영재",
//                    null,
//                    "나.. 사실..",
//                    "12:39",
//                    if (Random.nextInt() == 0) null else Random.nextInt(),
//                ),
//                TestChatItem(
//                    0,
//                    "노영재",
//                    null,
//                    "나.. 사실..",
//                    "12:39",
//                    if (Random.nextInt() == 0) null else Random.nextInt(),
//                ),
//                TestChatItem(
//                    0,
//                    "노영재",
//                    null,
//                    "나.. 사실..",
//                    "12:39",
//                    if (Random.nextInt() == 0) null else Random.nextInt(),
//                ),
//                TestChatItem(
//                    0,
//                    "노영재",
//                    null,
//                    "나.. 사실..",
//                    "12:39",
//                    if (Random.nextInt() == 0) null else Random.nextInt(),
//                ),
//                TestChatItem(
//                    0,
//                    "노영재",
//                    null,
//                    "나.. 사실..",
//                    "12:39",
//                    if (Random.nextInt() == 0) null else Random.nextInt(),
//                ),
//                TestChatItem(
//                    0,
//                    "노영재",
//                    null,
//                    "나.. 사실..",
//                    "12:39",
//                    if (Random.nextInt() == 0) null else Random.nextInt(),
//                ),
//                TestChatItem(
//                    0,
//                    "노영재",
//                    null,
//                    "나.. 사실..",
//                    "12:39",
//                    if (Random.nextInt() == 0) null else Random.nextInt(),
//                ),
//                TestChatItem(
//                    0,
//                    "노영재",
//                    null,
//                    "나.. 사실..",
//                    "12:39",
//                    if (Random.nextInt() == 0) null else Random.nextInt(),
//                ),
//                TestChatItem(
//                    0,
//                    "노영재",
//                    null,
//                    "나.. 사실..",
//                    "12:39",
//                    if (Random.nextInt() == 0) null else Random.nextInt(),
//                ),
//                TestChatItem(
//                    0,
//                    "노영재",
//                    null,
//                    "나.. 사실..",
//                    "12:39",
//                    if (Random.nextInt() == 0) null else Random.nextInt(),
//                ),
//                TestChatItem(
//                    0,
//                    "노영재",
//                    null,
//                    "나.. 사실..",
//                    "12:39",
//                    if (Random.nextInt() == 0) null else Random.nextInt(),
//                ),
//            ).toImmutableList(),
//        )
    }

    fun searchRoom(text: String) {
        _state.update {
            it.copy(
                filterMessage = text,
            )
        }
    }
}
