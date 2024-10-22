package com.seugi.network.timetable

import com.seugi.common.model.Result
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.timetable.response.TimetableResponse
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface TimetableDataSource {

    suspend fun getTimetableDay(workspaceId: String): BaseResponse<List<TimetableResponse>>

    suspend fun getTimetableWeekend(workspaceId: String): BaseResponse<List<TimetableResponse>>
}
