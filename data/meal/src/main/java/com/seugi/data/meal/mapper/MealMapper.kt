package com.seugi.data.meal.mapper

import com.seugi.common.utiles.toKotlinLocalDate
import com.seugi.data.meal.response.MealModel
import com.seugi.data.meal.response.MealType
import com.seugi.network.meal.response.MealResponse
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.toLocalDate

internal fun MealResponse.toModel() =
    MealModel(
        id = id,
        workspaceId = workspaceId,
        mealType = mealType.toMealType(),
        menu = menu.toImmutableList(),
        calorie = calorie,
        mealInfo = mealInfo.toImmutableList(),
        mealDate = mealDate.toKotlinLocalDate()
    )

internal fun String.toMealType() =
    when (this) {
        "조식" -> MealType.BREAKFAST
        "중식" -> MealType.LUNCH
        "석식" -> MealType.DINNER
        else -> MealType.DINNER
    }

internal fun List<MealResponse>.toModels() =
    this.map {
        it.toModel()
    }