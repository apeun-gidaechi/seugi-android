package com.seugi.data.schedule.repository

import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.schedule.ScheduleRepository
import com.seugi.data.schedule.mapper.toModels
import com.seugi.data.schedule.model.ScheduleModel
import com.seugi.network.core.response.safeResponse
import com.seugi.network.schedule.ScheduleDataSource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val scheduleDataSource: ScheduleDataSource,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher
): ScheduleRepository {
    override suspend fun getMonthSchedule(
        workspaceId: String,
        month: Int,
    ): Flow<Result<ImmutableList<ScheduleModel>>> = flow {
        val response = scheduleDataSource.getMonthSchedule(
            workspaceId, month
        ).safeResponse()

        emit(response.toModels().toImmutableList())
    }
        .flowOn(dispatcher)
        .asResult()


    override suspend fun yearSchedule(workspaceId: String): Flow<Result<ImmutableList<ScheduleModel>>>  = flow {
        val response = scheduleDataSource.getYearSchedule(
            workspaceId = workspaceId
        ).safeResponse()

        emit(response.toModels().toImmutableList())
    }
    .flowOn(dispatcher)
    .asResult()


}