package com.seugi.assignment.model

import com.seugi.data.assignment.model.AssignmentModel
import kotlinx.collections.immutable.ImmutableList

data class AssignmentUiState(
    val classroomTaskState: CommonUiState<ImmutableList<AssignmentModel>> = CommonUiState.Loading,
    val workspaceTaskState: CommonUiState<ImmutableList<AssignmentModel>> = CommonUiState.Loading,
)

sealed interface CommonUiState<out T> {
    data class Success<out T>(val data: T) : CommonUiState<T>
    data object Loading : CommonUiState<Nothing>
    data object Error : CommonUiState<Nothing>
    data object NotFound : CommonUiState<Nothing>
}
