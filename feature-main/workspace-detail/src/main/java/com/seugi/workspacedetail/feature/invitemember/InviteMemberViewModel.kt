package com.seugi.workspacedetail.feature.invitemember

import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.workspacedetail.feature.invitemember.model.RoomMemberItem
import com.seugi.workspacedetail.feature.invitemember.model.WaitMemberUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InviteMemberViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository
) : ViewModel() {

    private val _state = MutableStateFlow(WaitMemberUiState())
    val state = _state.asStateFlow()

    fun getWaitMembers(
        workspaceId: String,
        role: String
    ) {
        viewModelScope.launch {
            workspaceRepository.getWaitMembers(
                workspaceId = workspaceId,
                role = role
            ).collect {
                when (it) {
                    is Result.Success -> {
                        if (role == "STUDENT") {
                            val students = mutableListOf<RoomMemberItem>()
                            it.data.fastForEach {
                                students.add(
                                    RoomMemberItem(
                                        id = it.id,
                                        name = it.name,
                                        memberProfile = it.picture,
                                        checked = false,
                                    )
                                )
                            }
                            _state.update { ui ->
                                ui.copy(
                                    student = students.toImmutableList()
                                )
                            }
                        } else if (role == "TEACHER") {
                            val teachers = mutableListOf<RoomMemberItem>()
                            it.data.fastForEach {
                                teachers.add(
                                    RoomMemberItem(
                                        id = it.id,
                                        name = it.name,
                                        memberProfile = it.picture,
                                        checked = false,
                                    )
                                )
                            }
                            _state.update { ui ->
                                ui.copy(
                                    teacher = teachers.toImmutableList()
                                )
                            }
                        }
                    }

                    is Result.Error -> {}
                    is Result.Loading -> {}
                }
            }
        }
    }

    fun updateChecked(role: Int, memberId: Long) {
        if (role == 0) {
            _state.value = _state.value.copy(
                teacher = _state.value.teacher.map {
                    if (it.id == memberId) {
                        it.copy(
                            checked = it.checked.not(),
                        )
                    } else {
                        it
                    }
                }.toImmutableList()
            )
        }else{
            _state.value = _state.value.copy(
                student = _state.value.student.map {
                    if (it.id == memberId) {
                        it.copy(
                            checked = it.checked.not(),
                        )
                    } else {
                        it
                    }
                }.toImmutableList()
            )
        }
    }
}