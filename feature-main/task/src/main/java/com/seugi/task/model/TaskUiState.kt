package com.seugi.task.model

import com.seugi.data.task.model.TaskModel
import kotlinx.collections.immutable.ImmutableList

data class TaskUiState(
    val classroomTaskState: CommonUiState<ImmutableList<TaskModel>> = CommonUiState.Loading,
    val workspaceTaskState: CommonUiState<ImmutableList<TaskModel>> = CommonUiState.Loading,
)


sealed interface CommonUiState<out T> {
    data class Success<out T>(val data: T) : CommonUiState<T>
    data object Loading : CommonUiState<Nothing>
    data object Error : CommonUiState<Nothing>
    data object NotFound : CommonUiState<Nothing>
}
