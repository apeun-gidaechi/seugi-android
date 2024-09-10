package com.seugi.network.timetable

import com.seugi.network.core.response.BaseResponse
import com.seugi.network.timetable.response.TimetableResponse

interface TimetableDataSource {

    suspend fun getTimetableDay(workspaceId: String): BaseResponse<List<TimetableResponse>>
}
