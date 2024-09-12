package com.seugi.chatseugi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.chatseugi.model.ChatData
import com.seugi.chatseugi.model.ChatSeugiUiState
import com.seugi.common.model.Result
import com.seugi.data.catseugi.CatSeugiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ChatSeugiViewModel @Inject constructor(
    private val catSeugiRepository: CatSeugiRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ChatSeugiUiState())
    val state = _state.asStateFlow()

    fun sendMessage(message: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                chatMessage = it.chatMessage.toMutableList().apply {
                    add(0, ChatData.User(message, LocalDateTime.now().minusHours(9), true))
                }.toImmutableList(),
                isLoading = true,
            )
        }

        catSeugiRepository.sendText(
            text = message,
        ).collect {
            when (it) {
                is Result.Success -> {
                    val data = ChatData.AI(
                        message = it.data,
                        timestamp = LocalDateTime.now().minusHours(9),
                        isFirst = true,
                        isLast = true,
                    )
                    _state.update {
                        it.copy(
                            chatMessage = it.chatMessage.toMutableList().apply {
                                this.add(0, data)
                            }.toImmutableList(),
                            isLoading = false,
                        )
                    }
                }
                Result.Loading -> {}
                is Result.Error -> {
                    it.throwable.printStackTrace()
                    _state.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }
}
