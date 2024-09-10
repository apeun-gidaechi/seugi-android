package com.seugi.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.meal.MealRepository
import com.seugi.data.meal.response.MealType
import com.seugi.data.timetable.TimetableRepository
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.home.model.CommonUiState
import com.seugi.home.model.HomeUiMealState
import com.seugi.home.model.HomeUiState
import com.seugi.home.model.TimeScheduleUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDate
import java.time.LocalTime

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
    private val mealRepository: MealRepository,
    private val timetableRepository: TimetableRepository,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    fun load() {
        viewModelScope.launch(dispatcher) {
            val localWorkspaceId = workspaceRepository.getWorkspaceId()
            workspaceRepository.getMyWorkspaces().collect { response ->
                when (response) {
                    is Result.Success -> {
                        val workspaces = response.data
                        var workspaceId = ""
                        if (workspaces.isEmpty()) {
                            // 서버에 워크페이스가 없을때 워크페이스 가입
                            _state.update { mainUi ->
                                mainUi.copy(
                                    showDialog = true,
                                    schoolState = CommonUiState.NotFound,
                                    timeScheduleState = CommonUiState.NotFound,
                                    mealState = CommonUiState.NotFound,
                                    catSeugiState = CommonUiState.NotFound,
                                    schoolScheduleState = CommonUiState.NotFound,
                                )
                            }
                        } else {
                            // 워크페이스가 있다면 로컬에 아이디와 비교
                            if (localWorkspaceId.isEmpty()) {
                                // 로컬에 없으면 서버의 처음 워크페이스를 화면에
                                workspaceId = workspaces[0].workspaceId
                                _state.update { mainUi ->
                                    mainUi.copy(
                                        nowWorkspaceId = workspaces[0].workspaceId,
                                    )
                                }
                            } else {
                                workspaceId = localWorkspaceId
                                // 로컬에 있다면 로컬이랑 같은 아이디의 워크페이스를 화면에
                                workspaces.forEach {
                                    if (localWorkspaceId == it.workspaceId) {
                                        _state.update { mainUi ->
                                            mainUi.copy(
                                                nowWorkspaceId = it.workspaceId,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        insertLocal(workspaceId)
                        loadMeal(workspaceId)
                        getWorkspaceName(workspaceId)
                        loadTimetable(workspaceId)
                    }

                    is Result.Error -> {
                        response.throwable.printStackTrace()
                    }

                    Result.Loading -> {
                        _state.update {
                            it.copy(
                                schoolState = CommonUiState.Loading,
                                timeScheduleState = CommonUiState.Loading,
                                mealState = CommonUiState.Loading,
                                catSeugiState = CommonUiState.Loading,
                                schoolScheduleState = CommonUiState.Loading,
                            )
                        }
                    }
                }
            }
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
                        var homeUiMealState = HomeUiMealState()
                        it.data.forEach {
                            when (it.mealType) {
                                MealType.BREAKFAST ->
                                    homeUiMealState =
                                        homeUiMealState.copy(breakfast = it)

                                MealType.LUNCH -> homeUiMealState = homeUiMealState.copy(lunch = it)
                                MealType.DINNER ->
                                    homeUiMealState =
                                        homeUiMealState.copy(dinner = it)
                            }
                        }

                        _state.update { state ->
                            state.copy(
                                mealState = CommonUiState.Success(homeUiMealState),
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
                                if (it.data.isEmpty()) CommonUiState.NotFound
                                else CommonUiState.Success(
                                    TimeScheduleUiState(
                                        data = it.data.sortedBy { it.time }.toImmutableList(),
                                        startTime = LocalTime.of(8, 50),
                                        freeTimeSize = 10,
                                        timeSize = 50,
                                    )
                                ),
                        )
                    }
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    it.throwable.printStackTrace()
                    _state.update { state ->
                        state.copy(
                            timeScheduleState = CommonUiState.Error
                        )
                    }
                }
            }
        }
    }

    private fun getWorkspaceName(workspaceId: String) {
        viewModelScope.launch(dispatcher) {
            launch {
                workspaceRepository.getWorkspaceData(
                    workspaceId = workspaceId,
                ).collect { workspace ->
                    when (workspace) {
                        is Result.Success -> {
                            _state.update {
                                it.copy(
                                    schoolState = CommonUiState.Success(workspace.data.workspaceName),
                                )
                            }
                        }

                        is Result.Loading -> {
                            _state.update { state ->
                                state.copy(
                                    schoolState = CommonUiState.Loading,
                                    timeScheduleState = CommonUiState.Loading,
                                    mealState = CommonUiState.Loading,
                                    catSeugiState = CommonUiState.Loading,
                                    schoolScheduleState = CommonUiState.Loading,
                                )
                            }
                        }

                        else -> {
                        }
                    }
                }
            }
        }
    }

    private fun insertLocal(workspaceId: String) {
        viewModelScope.launch(dispatcher) {
            workspaceRepository.insertWorkspaceId(
                workspaceId = workspaceId,
            )
        }
    }
}
