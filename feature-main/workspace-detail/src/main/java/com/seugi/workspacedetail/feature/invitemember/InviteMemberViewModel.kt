package com.seugi.workspacedetail.feature.invitemember

import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.combineWhenAllComplete
import com.seugi.data.core.model.WorkspacePermissionModel
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.workspacedetail.feature.invitemember.model.InviteSideEffect
import com.seugi.workspacedetail.feature.invitemember.model.RoomMemberItem
import com.seugi.workspacedetail.feature.invitemember.model.WaitMemberUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class InviteMemberViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(WaitMemberUiState())
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<InviteSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun getWaitMembers(workspaceId: String) {
        viewModelScope.launch {
            combineWhenAllComplete(
                workspaceRepository.getWaitMembers(
                    workspaceId = workspaceId,
                    role = WorkspacePermissionModel.STUDENT.name,
                ),
                workspaceRepository.getWaitMembers(
                    workspaceId = workspaceId,
                    role = WorkspacePermissionModel.TEACHER.name,
                ),
            ) { student, teacher ->
                var listSize = 0
                when (student) {
                    is Result.Error -> {
                        student.throwable.printStackTrace()
                        _sideEffect.send(InviteSideEffect.Error)
                        return@combineWhenAllComplete
                    }
                    is Result.Loading -> {}
                    is Result.Success -> {
                        listSize += student.data.size
                        val students = mutableListOf<RoomMemberItem>()
                        student.data.fastForEach {
                            students.add(
                                RoomMemberItem(
                                    id = it.id,
                                    name = it.name,
                                    memberProfile = it.picture,
                                    checked = false,
                                ),
                            )
                        }
                        _state.update { ui ->
                            ui.copy(
                                student = students.toImmutableList(),
                            )
                        }
                    }
                }
                when (teacher) {
                    is Result.Error -> {
                        teacher.throwable.printStackTrace()
                        _sideEffect.send(InviteSideEffect.Error)
                        return@combineWhenAllComplete
                    }
                    is Result.Loading -> {}
                    is Result.Success -> {
                        listSize += teacher.data.size
                        val teachers = mutableListOf<RoomMemberItem>()
                        teacher.data.fastForEach {
                            teachers.add(
                                RoomMemberItem(
                                    id = it.id,
                                    name = it.name,
                                    memberProfile = it.picture,
                                    checked = false,
                                ),
                            )
                        }
                        _state.update { ui ->
                            ui.copy(
                                teacher = teachers.toImmutableList(),
                            )
                        }
                    }
                }
                _state.update {
                    it.copy(
                        waitMemberSize = listSize,
                    )
                }
            }.collect {}
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
                }.toImmutableList(),
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
                }.toImmutableList(),
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
                                workspaceCode = it.data,
                            )
                        }
                    }
                }
            }
        }
    }

    fun checkedMember(workspaceId: String, studentIds: List<Long>, teacherIds: List<Long>, feature: String) {
        viewModelScope.launch {
            // 선택된 유저가 없으면 리턴
            if (studentIds.isEmpty() && teacherIds.isEmpty()) {
                return@launch
                // 선택된 학생이 없으면 선생 신청 수락
            } else if (studentIds.isEmpty()) {
                addOrCancel(
                    workspaceId = workspaceId,
                    userSet = teacherIds,
                    role = WorkspacePermissionModel.TEACHER.name,
                    feature = feature,
                )
                // 선택된 선생이 없으면 학생 신청 수락
            } else if (teacherIds.isEmpty()) {
                addOrCancel(
                    workspaceId = workspaceId,
                    userSet = studentIds,
                    role = WorkspacePermissionModel.STUDENT.name,
                    feature = feature,
                )
                // 둘다 선택되었으면 두번 요청
            } else {
                val jab1 = async {
                    addOrCancel(
                        workspaceId = workspaceId,
                        userSet = studentIds,
                        role = WorkspacePermissionModel.STUDENT.name,
                        feature = feature,
                    )
                    addOrCancel(
                        workspaceId = workspaceId,
                        userSet = teacherIds,
                        role = WorkspacePermissionModel.TEACHER.name,
                        feature = feature,
                    )
                }
                jab1.start()
            }
        }
    }

    private fun addOrCancel(workspaceId: String, userSet: List<Long>, role: String, feature: String) {
        if (feature == "수락") {
            viewModelScope.launch {
                workspaceRepository.addMember(
                    workspaceId = workspaceId,
                    userSet = userSet,
                    role = role,
                ).collect {
                    when (it) {
                        is Result.Error -> {
                            _sideEffect.send(InviteSideEffect.FilledAdd)
                            it.throwable.printStackTrace()
                        }
                        is Result.Loading -> {}
                        is Result.Success -> {
                            _sideEffect.send(InviteSideEffect.SuccessAdd)
                            filterMember(role = role, userSet = userSet)
                        }
                    }
                }
            }
        } else if (feature == "거절") {
            viewModelScope.launch {
                workspaceRepository.cancelMember(
                    workspaceId = workspaceId,
                    userSet = userSet,
                    role = role,
                ).collect {
                    when (it) {
                        is Result.Error -> {
                            _sideEffect.send(InviteSideEffect.FilledCancel)
                            it.throwable.printStackTrace()
                        }
                        is Result.Loading -> {}
                        is Result.Success -> {
                            _sideEffect.send(InviteSideEffect.SuccessCancel)
                            filterMember(role = role, userSet = userSet)
                        }
                    }
                }
            }
        }
    }

    private fun filterMember(role: String, userSet: List<Long>) {
        _state.update { ui ->
            if (role == "TEACHER") {
                ui.copy(
                    teacher = ui.teacher.filter {
                        it.id !in userSet
                    }.toImmutableList(),
                )
            } else {
                ui.copy(
                    student = ui.student.filter {
                        it.id !in userSet
                    }.toImmutableList(),
                )
            }
        }
    }
}
