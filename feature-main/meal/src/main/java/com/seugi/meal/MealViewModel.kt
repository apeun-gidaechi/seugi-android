package com.seugi.meal

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.core.model.MealType
import com.seugi.data.meal.MealRepository
import com.seugi.meal.model.MealFilterUiState
import com.seugi.meal.model.MealUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.toJavaLocalDate
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val mealRepository: MealRepository
): ViewModel() {

    private val _state = MutableStateFlow(MealUiState())
    val state = _state.asStateFlow()

    fun loadMeal(
        workspaceId: String,
    ) = viewModelScope.launch {
        val date = LocalDate.now()
        mealRepository.getMonthMeal(
            workspaceId = workspaceId,
            year = date.year,
            month = date.monthValue
        ).collect {
            when (it) {
                is Result.Success -> {
                    var mealFilteredUiState = MealFilterUiState()
                    it.data
                        .filter {
                            it.mealDate.toJavaLocalDate() == date
                        }
                        .forEach {
                            mealFilteredUiState = when (it.mealType) {
                                MealType.BREAKFAST ->
                                    mealFilteredUiState.copy(breakfast = it)

                                MealType.LUNCH -> mealFilteredUiState.copy(lunch = it)
                                MealType.DINNER ->
                                    mealFilteredUiState.copy(dinner = it)
                            }
                        }
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            mealData = it.data,
                            filterMealData = mealFilteredUiState
                        )
                    }
                }
                Result.Loading -> {}
                is Result.Error -> {
                    it.throwable.printStackTrace()
                }
            }
        }
    }

    fun changeDate(date: LocalDate) {
        var mealFilteredUiState = MealFilterUiState()
        state.value.mealData
            .filter {
                it.mealDate.toJavaLocalDate() == date
            }
            .forEach {
                Log.d("TAG", "changeDate: $it")
                mealFilteredUiState = when (it.mealType) {
                    MealType.BREAKFAST ->
                        mealFilteredUiState.copy(breakfast = it)

                    MealType.LUNCH -> mealFilteredUiState.copy(lunch = it)
                    MealType.DINNER ->
                        mealFilteredUiState.copy(dinner = it)
                }
            }

        _state.update {
            it.copy(
                filterMealData = mealFilteredUiState
            )
        }
    }
}