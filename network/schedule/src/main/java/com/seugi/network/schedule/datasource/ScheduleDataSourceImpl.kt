package com.seugi.network.schedule.datasource

import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.schedule.ScheduleDataSource
import com.seugi.network.schedule.response.ScheduleResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import kotlinx.datetime.plus

class ScheduleDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : ScheduleDataSource {
    override suspend fun getMonthSchedule(workspaceId: String, month: Int): BaseResponse<List<ScheduleResponse>> = httpClient.get(SeugiUrl.Schedule.MONTH) {
        parameter("workspaceId", workspaceId)
        parameter("month", month)
    }.body()

    override suspend fun getYearSchedule(workspaceId: String): BaseResponse<List<ScheduleResponse>> = httpClient.get("${SeugiUrl.SCHEDULE}/$workspaceId").body()
}
