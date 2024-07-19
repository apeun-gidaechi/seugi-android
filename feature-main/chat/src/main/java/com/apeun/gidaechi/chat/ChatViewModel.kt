package com.apeun.gidaechi.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apeun.gidaechi.chat.model.ChatUiState
import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.data.personalchat.PersonalChatRepository
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

    fun loadChats() = viewModelScope.launch(Dispatchers.IO) {
        personalChatRepository.getAllChat("664bdd0b9dfce726abd30462").collect {
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
