package com.apeun.gidaechi.home.model

data class HomeUiState(
    val showShimmer: Boolean = true,
    val schoolState: CommonUiState<String> = CommonUiState.Loading,
    val timeScheduleState: CommonUiState<String> = CommonUiState.Loading,
    val mealState: CommonUiState<Triple<String, String, String>> = CommonUiState.Loading,
    val catSeugiState: CommonUiState<List<String>> = CommonUiState.Loading,
    val schoolScheduleState: CommonUiState<List<Triple<String, String, String>>> = CommonUiState.Loading,
)

sealed interface CommonUiState<out T> {
    data class Success<out T>(val data: T) : CommonUiState<T>
    data object Loading : CommonUiState<Nothing>
    data object Error : CommonUiState<Nothing>
    data object NotFound : CommonUiState<Nothing>
}
