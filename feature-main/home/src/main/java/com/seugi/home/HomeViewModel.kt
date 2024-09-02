package com.seugi.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.meal.MealRepository
import com.seugi.data.meal.response.MealType
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.home.model.CommonUiState
import com.seugi.home.model.HomeUiMealState
import com.seugi.home.model.HomeUiState
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

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
    private val mealRepository: MealRepository,
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
                    }

                    is Result.Error -> {}

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

    fun schoolChange(school: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                showDialog = true,
                schoolState = CommonUiState.Loading,
                timeScheduleState = CommonUiState.Loading,
                mealState = CommonUiState.Loading,
                catSeugiState = CommonUiState.Loading,
                schoolScheduleState = CommonUiState.Loading,
            )
        }
        delay(2000)
        if (school == "대구소프트웨어마이스터고등학교") {
            _state.update {
                it.copy(
                    showDialog = false,
                    schoolState = CommonUiState.Success("대구 소프트웨어 마이스터 고등학교"),
                    timeScheduleState = CommonUiState.Success(
                        listOf(
                            "진로",
                            "소공",
                            "소공",
                            "인공지능 수학",
                            "한국사",
                            "실용영어",
                            "웹프",
                        ).toImmutableList(),
                    ),
                    catSeugiState = CommonUiState.Success(
                        listOf(
                            "급식에 복어가 나오는 날이 언제...",
                            "우리 학교 대회 담당하는 분이 누구...",
                        ).toImmutableList(),
                    ),
                    schoolScheduleState = CommonUiState.Success(
                        listOf(
                            Triple("7/21", "체육대회", "D-9"),
                            Triple("7/23", "여름 교내 해커톤", "D-11"),
                            Triple("7/25", "KBS 촬영", "D-13"),
                        ).toImmutableList(),
                    ),
                )
            }
        } else {
            _state.update {
                it.copy(
                    showDialog = false,
                    schoolState = CommonUiState.Success("한국 디지털미디어 고등학교"),
                    timeScheduleState = CommonUiState.Success(
                        listOf(
                            "국어",
                            "체육",
                            "회계 원리",
                            "컴퓨터시스템",
                            "중국어",
                            "수학Ⅰ",
                            "영어",
                        ).toImmutableList(),
                    ),
                    catSeugiState = CommonUiState.Success(
                        listOf(
                            "급식에 복어가 나오는 날이 언제...",
                            "우리 학교 대회 담당하는 분이 누구...",
                        ).toImmutableList(),
                    ),
                    schoolScheduleState = CommonUiState.Success(
                        listOf(
                            Triple("7/16", "동문회 직업 소개의...", "D-4"),
                            Triple("7/19", "1학년 건강검진", "D-7"),
                            Triple("7/30", "여름방학식", "D-18"),
                        ).toImmutableList(),
                    ),

                )
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
