package com.seugi.network.schedule

import androidx.annotation.IntRange
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.schedule.response.ScheduleResponse

interface ScheduleDataSource {
    suspend fun getMonthSchedule(workspaceId: String, @IntRange(1, 12) month: Int): BaseResponse<List<ScheduleResponse>>

    suspend fun getYearSchedule(workspaceId: String): BaseResponse<List<ScheduleResponse>>
}
