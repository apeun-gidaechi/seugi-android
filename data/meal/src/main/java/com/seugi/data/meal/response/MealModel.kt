package com.seugi.data.meal.response

import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalDate

data class MealModel(
    val id: Long,
    val workspaceId: String,
    val mealType: MealType,
    val menu: ImmutableList<String>,
    val calorie: String,
    val mealInfo: ImmutableList<String>,
    val mealDate: LocalDate
)
