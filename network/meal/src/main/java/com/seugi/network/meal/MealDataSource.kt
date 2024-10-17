package com.seugi.network.meal

import com.seugi.network.core.response.BaseResponse
import com.seugi.network.meal.response.MealResponse
import kotlinx.datetime.LocalDate

interface MealDataSource {

    suspend fun getDateMeal(workspaceId: String, date: LocalDate): BaseResponse<List<MealResponse>>
}
