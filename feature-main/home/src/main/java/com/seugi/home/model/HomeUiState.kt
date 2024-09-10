package com.seugi.home.model

import com.seugi.data.meal.response.MealModel
import com.seugi.data.timetable.model.TimetableModel
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDateTime
import java.time.LocalTime

data class HomeUiState(
    val showDialog: Boolean = false,
    val nowWorkspaceId: String = "",
    val schoolState: CommonUiState<String> = CommonUiState.Loading,
    val timeScheduleState: CommonUiState<TimeScheduleUiState> = CommonUiState.Loading,
    val mealState: CommonUiState<HomeUiMealState> = CommonUiState.Loading,
    val catSeugiState: CommonUiState<ImmutableList<String>> = CommonUiState.Loading,
    val schoolScheduleState: CommonUiState<ImmutableList<Triple<String, String, String>>> = CommonUiState.Loading,
)

sealed interface CommonUiState<out T> {
    data class Success<out T>(val data: T) : CommonUiState<T>
    data object Loading : CommonUiState<Nothing>
    data object Error : CommonUiState<Nothing>
    data object NotFound : CommonUiState<Nothing>
}

data class HomeUiMealState(
    val breakfast: MealModel? = null,
    val lunch: MealModel? = null,
    val dinner: MealModel? = null,
)

data class TimeScheduleUiState(
    val data: ImmutableList<TimetableModel>,
    val startTime: LocalTime,
    val freeTimeSize: Int,
    val timeSize: Int,
)
