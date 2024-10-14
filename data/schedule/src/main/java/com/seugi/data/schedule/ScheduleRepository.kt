package com.seugi.data.schedule

import androidx.annotation.IntRange
import com.seugi.common.model.Result
import com.seugi.data.schedule.model.ScheduleModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {

    suspend fun getMonthSchedule(workspaceId: String, @IntRange(1, 12) month: Int): Flow<Result<ImmutableList<ScheduleModel>>>

    suspend fun yearSchedule(workspaceId: String): Flow<Result<ImmutableList<ScheduleModel>>>
}
