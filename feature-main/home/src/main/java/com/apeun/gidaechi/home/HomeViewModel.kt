package com.apeun.gidaechi.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apeun.gidaechi.home.model.CommonUiState
import com.apeun.gidaechi.home.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
//            _state.update {
//                it.copy(
//                    showShimmer = true,
//                    schoolState = CommonUiState.NotFound,
//                    timeScheduleState = CommonUiState.NotFound,
//                    mealState = CommonUiState.NotFound,
//                    catSeugiState = CommonUiState.NotFound,
//                    schoolScheduleState = CommonUiState.NotFound,
//                )
//            }
            delay(4000)
            _state.update {
                it.copy(
                    showShimmer = false,
                    schoolState = CommonUiState.Success("대구 소프트웨어 마이스터 고등학교"),
                    timeScheduleState = CommonUiState.Success("대구 소프트웨어 마이스터 고등학교"),
                    mealState = CommonUiState.Success(
                        Triple(
                            first = "오리훈제볶음밥\n간장두부조림\n배추김치\n초코첵스시리얼+우유\n오렌지",
                            second = "오리훈제볶음밥\n간장두부조림\n배추김치\n초코첵스시리얼+우유\n오렌지",
                            third = "오리훈제볶음밥\n간장두부조림\n배추김치\n초코첵스시리얼+우유\n오렌지")
                    ),
                    catSeugiState = CommonUiState.Success(listOf("급식에 복어가 나오는 날이 언제...", "우리 학교 대회 담당하는 분이 누구...")),
                    schoolScheduleState = CommonUiState.Success(
                        listOf(
                            Triple("7/21", "체육대회", "D-3"),
                            Triple("7/23", "여름 교내 해커톤", "D-5"),
                            Triple("7/25", "KBS 촬영", "D-7"),
                        )
                    )

                )
            }
        }
    }
}