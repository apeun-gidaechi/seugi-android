package com.seugi.meal.model

import com.seugi.data.core.model.MealModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MealUiState(
    val isLoading: Boolean = false,
    val mealData: ImmutableList<MealModel> = persistentListOf()
)