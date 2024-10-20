package com.seugi.workspacedetail.feature.invitemember

import android.util.Log
import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.core.model.WorkspacePermissionModel
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

                    is Result.Error -> {
                        it.throwable.printStackTrace()
                    }

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
        } else {
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


    fun getWorkspaceCode(workspaceId: String) {
        viewModelScope.launch {
            workspaceRepository.getWorkspaceCode(workspaceId).collect {
                when (it) {
                    is Result.Error -> {
                        it.throwable.printStackTrace()
                    }

                    is Result.Loading -> {}
                    is Result.Success -> {
                        _state.update { ui ->
                            ui.copy(
                                workspaceCode = it.data
                            )
                        }
                    }
                }
            }
        }
    }

    fun checkedMember(
        workspaceId: String,
        studentIds: List<Long>,
        teacherIds: List<Long>,
        feature: String
    ) {
        // 선택된 유저가 없으면 리턴
        if (studentIds.isEmpty() && teacherIds.isEmpty()) {
            return
        // 선택된 학생이 없으면 선생 신청 수락
        } else if (studentIds.isEmpty()) {
            addOrCancel(
                workspaceId = workspaceId,
                userSet = teacherIds,
                role = WorkspacePermissionModel.TEACHER.name,
                feature = feature
            )
        // 선택된 선생이 없으면 학생 신청 수락
        } else if (teacherIds.isEmpty()) {
            addOrCancel(
                workspaceId = workspaceId,
                userSet = studentIds,
                role = WorkspacePermissionModel.STUDENT.name,
                feature = feature
            )
        // 둘다 선택되었으면 두번 요청
        } else {
            addOrCancel(
                workspaceId = workspaceId,
                userSet = studentIds,
                role = WorkspacePermissionModel.STUDENT.name,
                feature = feature
            )
            addOrCancel(
                workspaceId = workspaceId,
                userSet = teacherIds,
                role = WorkspacePermissionModel.TEACHER.name,
                feature = feature
            )
        }

    }

    private fun addOrCancel(
        workspaceId: String,
        userSet: List<Long>,
        role: String,
        feature: String
    ) {
        if (feature == "수락") {
            viewModelScope.launch {
                workspaceRepository.addMember(
                    workspaceId = workspaceId,
                    userSet = userSet,
                    role = role
                ).collect {
                    when (it) {
                        is Result.Error -> {
                            it.throwable.printStackTrace()
                        }
                        is Result.Loading -> {}
                        is Result.Success -> {}
                    }
                }
            }
        }else if (feature == "거절"){
            viewModelScope.launch {
                workspaceRepository.cancelMember(
                    workspaceId = workspaceId,
                    userSet = userSet,
                    role = role
                ).collect {
                    when (it) {
                        is Result.Error -> {
                            it.throwable.printStackTrace()
                        }
                        is Result.Loading -> {}
                        is Result.Success -> {}
                    }
                }
            }
        }
    }
}