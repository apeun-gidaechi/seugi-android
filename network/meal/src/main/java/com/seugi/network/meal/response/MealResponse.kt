package com.seugi.network.meal.response

import kotlinx.datetime.LocalDate

data class MealResponse(
    val id: Long,
    val workspaceId: String,
    val mealType: String,
    val menu: List<String>,
    val calorie: String,
    val mealInfo: List<String>,
    val mealDate: LocalDate,
)
