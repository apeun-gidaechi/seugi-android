package com.seugi.data.meal

import androidx.annotation.IntRange
import com.seugi.common.model.Result
import com.seugi.data.core.model.MealModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface MealRepository {

    suspend fun getDateMeal(workspaceId: String, date: LocalDate): Flow<Result<ImmutableList<MealModel>>>

    suspend fun getMonthMeal(workspaceId: String, @IntRange(1970) year: Int, @IntRange(1, 12) month: Int): Flow<Result<ImmutableList<MealModel>>>
}
