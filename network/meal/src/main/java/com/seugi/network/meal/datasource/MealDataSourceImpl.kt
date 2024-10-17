package com.seugi.network.meal.datasource

import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.meal.MealDataSource
import com.seugi.network.meal.response.MealResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class MealDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : MealDataSource {
    override suspend fun getDateMeal(workspaceId: String, date: LocalDate): BaseResponse<List<MealResponse>> = httpClient.get(SeugiUrl.Meal.ROOT) {
        parameter("workspaceId", workspaceId)
        parameter("date", date)
    }.body()
}
