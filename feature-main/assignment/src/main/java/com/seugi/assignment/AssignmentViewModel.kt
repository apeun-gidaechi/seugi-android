package com.seugi.assignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.assignment.model.AssignmentUiState
import com.seugi.assignment.model.CommonUiState
import com.seugi.common.model.Result
import com.seugi.data.assignment.AssignmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AssignmentViewModel @Inject constructor(
    private val assignmentRepository: AssignmentRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(AssignmentUiState())
    val state = _state.asStateFlow()

    fun getClassroomTask() = viewModelScope.launch {
        assignmentRepository.getGoogleTaskAll().collect {
            when (it) {
                is Result.Success -> {
                    _state.update { state ->
                        state.copy(
                            classroomTaskState = CommonUiState.Success(
                                it.data
                                    .sortedBy { it.dueDate }
                                    .toImmutableList(),
                            ),
                        )
                    }
                }
                Result.Loading -> {}
                is Result.Error -> {
                    _state.update { state ->
                        state.copy(
                            classroomTaskState = CommonUiState.Error,
                        )
                    }
                }
            }
        }
    }

    fun getWorkspaceTask(workspaceId: String) = viewModelScope.launch {
        assignmentRepository.getWorkspaceTaskAll(workspaceId).collect {
            when (it) {
                is Result.Success -> {
                    _state.update { state ->
                        state.copy(
                            workspaceTaskState = CommonUiState.Success(
                                it.data
                                    .sortedBy { it.dueDate }
                                    .toImmutableList(),
                            ),
                        )
                    }
                }
                Result.Loading -> {}
                is Result.Error -> {
                    _state.update { state ->
                        state.copy(
                            workspaceTaskState = CommonUiState.Error,
                        )
                    }
                    it.throwable.printStackTrace()
                }
            }
        }
    }
}
