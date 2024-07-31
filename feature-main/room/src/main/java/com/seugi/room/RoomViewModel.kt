package com.seugi.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.groupchat.GroupChatRepository
import com.seugi.room.model.RoomUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val groupChatRepository: GroupChatRepository,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow(RoomUiState())
    val state = _state.asStateFlow()

    fun loadChats() = viewModelScope.launch(dispatcher) {
        groupChatRepository.getGroupRoomList("664bdd0b9dfce726abd30462").collect {
            when (it) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        _chatItems = it.data
                            .sortedByDescending {
                                it.lastMessageTimestamp
                            }
                            .toImmutableList(),
                    )
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    it.throwable.printStackTrace()
                }
            }
        }
    }

    fun searchRoom(text: String) {
        _state.update {
            it.copy(
                filterMessage = text,
            )
        }
    }
}
