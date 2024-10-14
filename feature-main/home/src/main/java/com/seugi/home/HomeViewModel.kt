package com.seugi.home

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.meal.MealRepository
import com.seugi.data.meal.response.MealType
import com.seugi.data.schedule.ScheduleRepository
import com.seugi.data.timetable.TimetableRepository
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.data.workspace.model.WorkspaceModel
import com.seugi.designsystem.R
import com.seugi.home.model.CommonUiState
import com.seugi.home.model.HomeUiState
import com.seugi.home.model.MealUiState
import com.seugi.home.model.TimeScheduleUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDate

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
    private val mealRepository: MealRepository,
    private val timetableRepository: TimetableRepository,
    private val scheduleRepository: ScheduleRepository,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    fun load(workspace: WorkspaceModel) {
        viewModelScope.launch(dispatcher) {
            // 처음 한번만 로딩하는 로직 추후 추가.

            _state.update {
                it.copy(
                    schoolState = CommonUiState.Success(workspace.workspaceName),
                    timeScheduleState = CommonUiState.Loading,
                    mealState = CommonUiState.Loading,
                    catSeugiState = CommonUiState.Loading,
                    schoolScheduleState = CommonUiState.Loading,
                )
            }
            loadMeal(workspace.workspaceId)
            loadTimetable(workspace.workspaceId)
            loadCatSeugi()
            loadSchedule(workspace.workspaceId)
        }
    }

    fun setStateNotJoin() = viewModelScope.launch(dispatcher) {
        _state.update {
            it.copy(
                showDialog = true,
                schoolState = CommonUiState.Error,
                timeScheduleState = CommonUiState.Error,
                mealState = CommonUiState.Error,
                catSeugiState = CommonUiState.Error,
                schoolScheduleState = CommonUiState.Error,
            )
        }
    }

    private fun loadMeal(workspaceId: String) = viewModelScope.launch(dispatcher) {
        launch {
            mealRepository.getDateMeal(
                workspaceId = workspaceId,
                date = LocalDate.now().toKotlinLocalDate(),
            ).collect {
                when (it) {
                    is Result.Success -> {
                        var mealUiState = MealUiState()
                        it.data.forEach {
                            when (it.mealType) {
                                MealType.BREAKFAST ->
                                    mealUiState =
                                        mealUiState.copy(breakfast = it)

                                MealType.LUNCH -> mealUiState = mealUiState.copy(lunch = it)
                                MealType.DINNER ->
                                    mealUiState =
                                        mealUiState.copy(dinner = it)
                            }
                        }

                        _state.update { state ->
                            state.copy(
                                mealState = CommonUiState.Success(mealUiState),
                            )
                        }
                    }

                    is Result.Error -> {
                        _state.update { state ->
                            state.copy(
                                mealState = CommonUiState.Error,
                            )
                        }
                        it.throwable.printStackTrace()
                    }

                    is Result.Loading -> {}
                }
            }
        }
    }

    private fun loadTimetable(workspaceId: String) = viewModelScope.launch(dispatcher) {
        timetableRepository.getTimetableDay(workspaceId).collect {
            when (it) {
                is Result.Success -> {
                    _state.update { state ->
                        state.copy(
                            timeScheduleState =
                            if (it.data.isEmpty()) {
                                CommonUiState.NotFound
                            } else {
                                CommonUiState.Success(
                                    TimeScheduleUiState(
                                        data = it.data.sortedBy { it.time }.toImmutableList(),
                                        startTime = LocalTime.of(8, 50),
                                        freeTimeSize = 10,
                                        timeSize = 50,
                                    ),
                                )
                            },
                        )
                    }
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    it.throwable.printStackTrace()
                    _state.update { state ->
                        state.copy(
                            timeScheduleState = CommonUiState.Error,
                        )
                    }
                }
            }
        }
    }

    private fun loadCatSeugi() = viewModelScope.launch(dispatcher) {
        delay(1000)
        _state.update {
            it.copy(
                catSeugiState = CommonUiState.Success(persistentListOf()),
            )
        }
    }

    private fun loadSchedule(
        workspaceId: String
    ) = viewModelScope.launch(dispatcher) {
        val date = LocalDate.now()
        scheduleRepository.getMonthSchedule(
            workspaceId = workspaceId,
            month = date.monthValue
        ).collect {
            when (it) {
                is Result.Success -> {
                    val filterItem = it.data
                        .filter {
                            date.dayOfMonth <= it.date.dayOfMonth
                        }
                        .sortedBy {
                            it.date
                        }
                        .take(3)
                        .toImmutableList()
                    if (filterItem.isEmpty()) {
                        _state.update { uiState ->
                            uiState.copy(
                                schoolScheduleState = CommonUiState.Error
                            )
                        }
                    } else {
                        _state.update { uiState ->
                            uiState.copy(
                                schoolScheduleState = CommonUiState.Success(filterItem)
                            )
                        }
                    }
                }
                Result.Loading -> {}
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            schoolScheduleState = CommonUiState.Error,
                        )
                    }
                }
            }
        }
    }
}
