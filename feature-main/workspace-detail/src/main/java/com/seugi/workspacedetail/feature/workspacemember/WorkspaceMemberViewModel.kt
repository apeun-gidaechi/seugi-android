package com.seugi.workspacedetail.feature.workspacemember

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.personalchat.PersonalChatRepository
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.workspacedetail.feature.workspacemember.model.WorkspaceMemberSideEffect
import com.seugi.workspacedetail.feature.workspacemember.model.WorkspaceMemberUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class WorkspaceMemberViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
    private val personalChatRepository: PersonalChatRepository,
) : ViewModel() {

    private val _sideEffect = Channel<WorkspaceMemberSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _state = MutableStateFlow(WorkspaceMemberUiState())
    val state = _state.asStateFlow()
    fun getAllMember(workspaceId: String) {
        if (_state.value.member.size == 0) {
            _state.update {
                it.copy(
                    isLoading = true,
                )
            }
        }
        viewModelScope.launch {
            workspaceRepository.getMembers(workspaceId = workspaceId).collect {
                when (it) {
                    is Result.Success -> {
                        _state.update { ui ->
                            ui.copy(
                                member = it.data.toImmutableList(),
                                isLoading = false,
                            )
                        }
                    }
                    is Result.Error -> {
                    }
                    else -> {
                    }
                }
            }
        }
    }

    fun createChatRoom(workspaceId: String, targetUserId: Long) = viewModelScope.launch {
        personalChatRepository.createChat(
            workspaceId = workspaceId,
            roomName = "",
            joinUsers = listOf(targetUserId),
            chatRoomImg = "",
        ).collect {
            when (it) {
                is Result.Success -> {
                    _sideEffect.send(WorkspaceMemberSideEffect.SuccessCreateRoom(it.data))
                }
                is Result.Loading -> { }
                is Result.Error -> {
                    _sideEffect.send(WorkspaceMemberSideEffect.FailedCreateRoom)
                }
            }
        }
    }
}
