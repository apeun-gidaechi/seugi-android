package com.seugi.roomcreate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.groupchat.GroupChatRepository
import com.seugi.data.personalchat.PersonalChatRepository
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.roomcreate.model.RoomCreateSideEffect
import com.seugi.roomcreate.model.RoomCreateUiState
import com.seugi.roomcreate.model.RoomMemberItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RoomCreateViewModel @Inject constructor(
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
    private val workspaceRepository: WorkspaceRepository,
    private val personalChatRepository: PersonalChatRepository,
    private val groupChatRepository: GroupChatRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(RoomCreateUiState())
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<RoomCreateSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun loadUser(workspaceId: String, userId: Int) = viewModelScope.launch(dispatcher) {
        workspaceRepository.getMembers(workspaceId).collect {
            when (it) {
                is Result.Success -> {
                    val users = mutableListOf<RoomMemberItem>()
                    for (datum in it.data) {
                        users.add(
                            RoomMemberItem(
                                id = datum.member.id,
                                name = datum.nameAndNick,
                                memberProfile = datum.member.picture,
                                checked = false,
                            ),
                        )
                    }
                    _state.value = _state.value.copy(
                        userItem = users.filter { it.id != userId }.toImmutableList(),
                    )
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    it.throwable.printStackTrace()
                }
            }
        }
    }

    fun updateChecked(userId: Int) {
        _state.value = _state.value.copy(
            userItem = _state.value.userItem.map {
                if (it.id == userId) {
                    it.copy(
                        checked = it.checked.not(),
                    )
                } else {
                    it
                }
            }.toImmutableList(),
        )
    }

    fun createRoom(workspaceId: String, roomName: String) = viewModelScope.launch(dispatcher) {
        if (_state.value.checkedMemberState.size <= 1) {
            return@launch
        }
        groupChatRepository.createChat(
            workspaceId = workspaceId,
            roomName = roomName,
            joinUsers = _state.value.checkedMemberState.map { it.id },
            chatRoomImg = "",
        ).collect {
            when (it) {
                is Result.Success -> {
                    _sideEffect.send(RoomCreateSideEffect.SuccessCreateRoom(it.data, false))
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    it.throwable.printStackTrace()
                }
            }
        }
    }

    fun createRoom(workspaceId: String) = viewModelScope.launch(dispatcher) {
        if (_state.value.checkedMemberState.size != 1) {
            return@launch
        }
        personalChatRepository.createChat(
            workspaceId = workspaceId,
            roomName = "",
            joinUsers = _state.value.checkedMemberState.map { it.id },
            chatRoomImg = "",
        ).collect {
            when (it) {
                is Result.Success -> {
                    _sideEffect.send(RoomCreateSideEffect.SuccessCreateRoom(it.data, true))
                }

                is Result.Loading -> {}
                is Result.Error -> {
                    it.throwable.printStackTrace()
                }
            }
        }
    }
}
