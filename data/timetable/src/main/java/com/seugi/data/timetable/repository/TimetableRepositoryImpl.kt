package com.seugi.data.timetable.repository

import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.core.model.TimetableModel
import com.seugi.data.timetable.TimetableRepository
import com.seugi.data.timetable.mapper.toModels
import com.seugi.network.core.response.safeResponse
import com.seugi.network.timetable.TimetableDataSource
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TimetableRepositoryImpl @Inject constructor(
    private val timetableDataSource: TimetableDataSource,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : TimetableRepository {
    override suspend fun getTimetableDay(workspaceId: String): Flow<Result<ImmutableList<TimetableModel>>> = flow {
        val response = timetableDataSource.getTimetableDay(
            workspaceId = workspaceId,
        ).safeResponse()

        emit(response.toModels().toImmutableList())
    }
        .flowOn(dispatcher)
        .asResult()
}
