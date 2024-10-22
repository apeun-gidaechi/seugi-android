package com.seugi.data.meal.mapper

import com.seugi.common.utiles.toKotlinLocalDate
import com.seugi.common.utiles.toNotSpaceString
import com.seugi.data.core.mapper.toMealType
import com.seugi.data.core.model.MealModel
import com.seugi.local.room.model.MealEntity
import kotlinx.collections.immutable.toImmutableList

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

@JvmName("ListMealEntitytoModels")
internal fun List<MealEntity>.toModels() = this.map {
    it.toModel()
}

@JvmName("ListMealModeltoEntity")
internal fun List<MealModel>.toEntity() = this.map {
    it.toEntity()
}
