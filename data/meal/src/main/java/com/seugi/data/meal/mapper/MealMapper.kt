package com.seugi.data.meal.mapper

import com.seugi.common.utiles.toKotlinLocalDate
import com.seugi.common.utiles.toNotSpaceString
import com.seugi.data.meal.response.MealModel
import com.seugi.data.meal.response.MealType
import com.seugi.local.room.model.MealEntity
import com.seugi.network.meal.response.MealResponse
import kotlinx.collections.immutable.toImmutableList

internal fun MealResponse.toModel() = MealModel(
    id = id,
    workspaceId = workspaceId,
    mealType = mealType.toMealType(),
    menu = menu.toImmutableList(),
    calorie = calorie,
    mealInfo = mealInfo.toImmutableList(),
    mealDate = mealDate.toKotlinLocalDate(),
)

internal fun MealEntity.toModel() = MealModel(
    id = id,
    workspaceId = workspaceId,
    mealType = mealType.toMealType(),
    menu = menu.split("||").toImmutableList(),
    calorie = calorie,
    mealInfo = mealInfo.split("||").toImmutableList(),
    mealDate = mealDate.toKotlinLocalDate(),
)

internal fun MealModel.toEntity() = MealEntity(
    id = id,
    workspaceId = workspaceId,
    mealType = mealType.name,
    menu = menu.joinToString("||"),
    calorie = calorie,
    mealInfo = mealInfo.joinToString("||"),
    mealDate = mealDate.toNotSpaceString(),
)

internal fun String.toMealType() = when (this) {
    "조식" -> MealType.BREAKFAST
    "중식" -> MealType.LUNCH
    "BREAKFAST" -> MealType.BREAKFAST
    "LUNCH" -> MealType.LUNCH
    else -> MealType.DINNER
}

@JvmName("ListMealResponsetoModels")
internal fun List<MealResponse>.toModels() = this.map {
    it.toModel()
}

@JvmName("ListMealEntitytoModels")
internal fun List<MealEntity>.toModels() = this.map {
    it.toModel()
}

@JvmName("ListMealModeltoEntity")
internal fun List<MealModel>.toEntity() = this.map {
    it.toEntity()
}
