package com.seugi.meal.model

import com.seugi.data.core.model.MealModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MealUiState(
    val isLoading: Boolean = true,
    val mealData: ImmutableList<MealModel> = persistentListOf(),
    val filterMealData: MealFilterUiState = MealFilterUiState(),
)

data class MealFilterUiState(
    val breakfast: MealModel? = null,
    val lunch: MealModel? = null,
    val dinner: MealModel? = null,
)
