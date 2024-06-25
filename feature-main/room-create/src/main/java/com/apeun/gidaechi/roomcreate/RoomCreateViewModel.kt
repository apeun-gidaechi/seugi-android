package com.apeun.gidaechi.roomcreate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.common.utiles.DispatcherType
import com.apeun.gidaechi.common.utiles.SeugiDispatcher
import com.apeun.gidaechi.data.workspace.WorkSpaceRepository
import com.apeun.gidaechi.roomcreate.model.RoomCreateUiState
import com.apeun.gidaechi.roomcreate.model.RoomMemberItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RoomCreateViewModel @Inject constructor(
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
    private val workSpaceRepository: WorkSpaceRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RoomCreateUiState())
    val state = _state.asStateFlow()

    fun loadUser(
        workspaceId: String
    ) = viewModelScope.launch(dispatcher) {

        workSpaceRepository.getMembers(workspaceId).collect {
            when(it) {
                is Result.Success -> {
                    val users = mutableListOf<RoomMemberItem>()
                    for (datum in it.data) {
                        users.add(
                            RoomMemberItem(
                                id = datum.member.id,
                                name = datum.nick,
                                memberProfile = datum.member.picture,
                                checked = false
                            )
                        )
                    }
                    _state.value = _state.value.copy(
                        userItem = users.toImmutableList(),
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
}
