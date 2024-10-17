package com.seugi.home.model

import com.seugi.data.meal.response.MealModel
import com.seugi.data.schedule.model.ScheduleModel
import com.seugi.data.timetable.model.TimetableModel
import java.time.LocalTime
import kotlinx.collections.immutable.ImmutableList

data class HomeUiState(
    val nowWorkspaceId: String = "",
    val schoolState: CommonUiState<String> = CommonUiState.Loading,
    val timeScheduleState: CommonUiState<TimeScheduleUiState> = CommonUiState.Loading,
    val mealState: CommonUiState<MealUiState> = CommonUiState.Loading,
    val catSeugiState: CommonUiState<ImmutableList<String>> = CommonUiState.Loading,
    val schoolScheduleState: CommonUiState<ImmutableList<ScheduleModel>> = CommonUiState.Loading,
)

sealed interface CommonUiState<out T> {
    data class Success<out T>(val data: T) : CommonUiState<T>
    data object Loading : CommonUiState<Nothing>
    data object Error : CommonUiState<Nothing>
    data object NotFound : CommonUiState<Nothing>
}

data class MealUiState(
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
