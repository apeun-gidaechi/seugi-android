package com.seugi.network.meal

import com.seugi.network.core.response.BaseResponse
import com.seugi.network.meal.response.MealResponse

interface MealDataSource {

    suspend fun getDateMeal(workspaceId: String, date: String): BaseResponse<List<MealResponse>>
}
