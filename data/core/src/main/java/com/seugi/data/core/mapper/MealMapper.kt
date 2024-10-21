package com.seugi.data.core.mapper

import com.seugi.data.core.model.MealModel
import com.seugi.data.core.model.MealType
import com.seugi.network.meal.response.MealResponse
import kotlinx.collections.immutable.toImmutableList

fun MealResponse.toModel() = MealModel(
    id = id,
    workspaceId = workspaceId,
    mealType = mealType.toMealType(),
    menu = menu.toImmutableList(),
    calorie = calorie,
    mealInfo = mealInfo.toImmutableList(),
    mealDate = mealDate,
)

fun String.toMealType() = when (this) {
    "조식" -> MealType.BREAKFAST
    "중식" -> MealType.LUNCH
    "BREAKFAST" -> MealType.BREAKFAST
    "LUNCH" -> MealType.LUNCH
    else -> MealType.DINNER
}


@JvmName("ListMealResponsetoModels")
fun List<MealResponse>.toModels() = this.map {
    it.toModel()
}