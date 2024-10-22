package com.seugi.network.timetable.datasource

import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.timetable.TimetableDataSource
import com.seugi.network.timetable.response.TimetableResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class TimetableDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : TimetableDataSource {
    override suspend fun getTimetableDay(workspaceId: String): BaseResponse<List<TimetableResponse>> = httpClient.get(SeugiUrl.Timetable.DAY) {
        parameter("workspaceId", workspaceId)
    }.body()

    override suspend fun getTimetableWeekend(workspaceId: String): BaseResponse<List<TimetableResponse>> = httpClient.get(SeugiUrl.Timetable.WEEKEND) {
        parameter("workspaceId", workspaceId)
    }.body()
}
