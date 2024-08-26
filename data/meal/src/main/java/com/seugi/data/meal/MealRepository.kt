package com.seugi.data.meal

import com.seugi.common.model.Result
import com.seugi.data.meal.response.MealModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface MealRepository {

    suspend fun getDateMeal(workspaceId: String, date: LocalDate): Flow<Result<ImmutableList<MealModel>>>
}
