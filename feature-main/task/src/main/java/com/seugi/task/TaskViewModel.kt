package com.seugi.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.task.TaskRepository
import com.seugi.task.model.CommonUiState
import com.seugi.task.model.TaskUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(TaskUiState())
    val state = _state.asStateFlow()

    fun getClassroomTask() = viewModelScope.launch {
        taskRepository.getGoogleTaskAll().collect {
            when (it) {
                is Result.Success -> {
                    _state.update { state ->
                        state.copy(
                            classroomTaskState = CommonUiState.Success(it.data),
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
        taskRepository.getWorkspaceTaskAll(workspaceId).collect {
            when (it) {
                is Result.Success -> {
                    _state.update { state ->
                        state.copy(
                            workspaceTaskState = CommonUiState.Success(it.data),
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
