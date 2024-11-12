package com.seugi.data.assignment.repository

import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.assignment.AssignmentRepository
import com.seugi.data.assignment.mapper.toModels
import com.seugi.data.assignment.model.AssignmentModel
import com.seugi.network.core.response.safeResponse
import com.seugi.network.assignment.AssignmentDataSource
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AssignmentRepositoryImpl @Inject constructor(
    private val assignmentDataSource: AssignmentDataSource,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : AssignmentRepository {
    override suspend fun getWorkspaceTaskAll(workspaceId: String) = flow {
        val response = assignmentDataSource.getWorkspaceTaskAll(
            workspaceId = workspaceId,
        ).safeResponse()

        emit(response.toModels().toImmutableList())
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun getGoogleTaskAll(): Flow<Result<ImmutableList<AssignmentModel>>> = flow {
        val response = assignmentDataSource.getClassroomAll().safeResponse()

        emit(response.toModels().toImmutableList())
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun createTask(workspaceId: String, title: String, description: String, dueDate: LocalDateTime) = flow {
        val response = assignmentDataSource.createTask(
            workspaceId = workspaceId,
            title = title,
            description = description,
            dueDate = dueDate,
        ).safeResponse()

        emit(response)
    }
        .flowOn(dispatcher)
        .asResult()
}
