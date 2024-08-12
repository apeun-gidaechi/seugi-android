package com.seugi.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.home.model.CommonUiState
import com.seugi.home.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val workspaces = workspaceRepository.getAllWorkspaces()
            Log.d("TAG", "workspaces ${workspaceRepository.getAllWorkspaces()} ")

            delay(2000)

            if (workspaces.isEmpty()) {

                _state.update {
                    it.copy(
                        showDialog = true,
                        schoolState = CommonUiState.NotFound,
                        timeScheduleState = CommonUiState.NotFound,
                        mealState = CommonUiState.NotFound,
                        catSeugiState = CommonUiState.NotFound,
                        schoolScheduleState = CommonUiState.NotFound,
                    )
                }
            } else {
                Log.d("TAG", ":있음 ")
                _state.update {
                    it.copy(
                        showDialog = false,
                        schoolState = CommonUiState.Success("대구 소프트웨어 마이스터 고등학교"),
                        timeScheduleState = CommonUiState.Success(listOf("진로", "소공", "소공", "인공지능 수학", "한국사", "실용영어", "웹프").toImmutableList()),
                        mealState = CommonUiState.Success(
                            Triple(
                                first = Pair("쇠고기 야채죽\n연유프렌치토스트\n배추김치\n포도\n허니초코크런치시리얼+우유", "602Kcal"),
                                second = Pair("추가밥\n매콤로제해물파스타\n#브리오슈수제버거\n모듬야채피클\n맥케인\n망고사고", "1,443Kcal"),
                                third = Pair("현미밥\n돼지국밥\n삼색나물무침\n-오징어야채볶음\n석박지", "774Kcal"),
                            ),
                        ),
                        catSeugiState = CommonUiState.Success(listOf("급식에 복어가 나오는 날이 언제...", "우리 학교 대회 담당하는 분이 누구...").toImmutableList()),
                        schoolScheduleState = CommonUiState.Success(
                            listOf(
                                Triple("7/21", "체육대회", "D-9"),
                                Triple("7/23", "여름 교내 해커톤", "D-11"),
                                Triple("7/25", "KBS 촬영", "D-13"),
                            ).toImmutableList(),
                        ),
                    )
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
                    timeScheduleState = CommonUiState.Success(listOf("진로", "소공", "소공", "인공지능 수학", "한국사", "실용영어", "웹프").toImmutableList()),
                    mealState = CommonUiState.Success(
                        Triple(
                            first = Pair("쇠고기 야채죽\n연유프렌치토스트\n배추김치\n포도\n허니초코크런치시리얼+우유", "602Kcal"),
                            second = Pair("추가밥\n매콤로제해물파스타\n#브리오슈수제버거\n모듬야채피클\n맥케인\n망고사고", "1,443Kcal"),
                            third = Pair("현미밥\n돼지국밥\n삼색나물무침\n-오징어야채볶음\n석박지", "774Kcal"),
                        ),
                    ),
                    catSeugiState = CommonUiState.Success(listOf("급식에 복어가 나오는 날이 언제...", "우리 학교 대회 담당하는 분이 누구...").toImmutableList()),
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
                    timeScheduleState = CommonUiState.Success(listOf("국어", "체육", "회계 원리", "컴퓨터시스템", "중국어", "수학Ⅰ", "영어").toImmutableList()),
                    mealState = CommonUiState.Success(
                        Triple(
                            first = Pair("급식이 없습니다.", "0Kcal"),
                            second = Pair("낙지잠발라야볶음밥 \n고르곤졸라피자*개별꿀 \n계란파국 \n양상추샐러드*오리엔탈드레싱 \n배추김치 \n망고파인애플플리또", "531.6Kcal"),
                            third = Pair("급식이 없습니다.", "0Kcal"),
                        ),
                    ),
                    catSeugiState = CommonUiState.Success(listOf("급식에 복어가 나오는 날이 언제...", "우리 학교 대회 담당하는 분이 누구...").toImmutableList()),
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
}
