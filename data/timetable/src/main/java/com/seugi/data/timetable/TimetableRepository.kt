package com.seugi.data.timetable

import com.seugi.common.model.Result
import com.seugi.data.core.model.TimetableModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface TimetableRepository {

    suspend fun getTimetableDay(workspaceId: String): Flow<Result<ImmutableList<TimetableModel>>>

    suspend fun getTimetableWeekend(workspaceId: String): Flow<Result<ImmutableList<TimetableModel>>>
}
