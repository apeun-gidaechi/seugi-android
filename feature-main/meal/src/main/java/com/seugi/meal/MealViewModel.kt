package com.seugi.meal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.data.meal.MealRepository
import com.seugi.meal.model.MealUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    }
}