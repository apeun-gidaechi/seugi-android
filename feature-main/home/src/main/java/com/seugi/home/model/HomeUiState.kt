package com.seugi.home.model

import kotlinx.collections.immutable.ImmutableList

data class HomeUiState(
    val showShimmer: Boolean = true,
    val schoolState: CommonUiState<String> = CommonUiState.Loading,
    val timeScheduleState: CommonUiState<ImmutableList<String>> = CommonUiState.Loading,
    val mealState: CommonUiState<Triple<Pair<String, String>, Pair<String, String>, Pair<String, String>>> = CommonUiState.Loading,
    val catSeugiState: CommonUiState<ImmutableList<String>> = CommonUiState.Loading,
    val schoolScheduleState: CommonUiState<ImmutableList<Triple<String, String, String>>> = CommonUiState.Loading,
)

sealed interface CommonUiState<out T> {
    data class Success<out T>(val data: T) : CommonUiState<T>
    data object Loading : CommonUiState<Nothing>
    data object Error : CommonUiState<Nothing>
    data object NotFound : CommonUiState<Nothing>
}
