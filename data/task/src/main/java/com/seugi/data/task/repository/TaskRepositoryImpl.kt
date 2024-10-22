package com.seugi.data.task.repository

import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.task.TaskRepository
import com.seugi.data.task.mapper.toModels
import com.seugi.data.task.model.TaskModel
import com.seugi.network.core.response.safeResponse
import com.seugi.network.task.TaskDataSource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDataSource: TaskDataSource,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher
): TaskRepository {
    override suspend fun getWorkspaceTaskAll(workspaceId: String) = flow {
        val response = taskDataSource.getWorkspaceTaskAll(
            workspaceId = workspaceId
        ).safeResponse()

        emit(response.toModels().toImmutableList())
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun getGoogleTaskAll(): Flow<Result<ImmutableList<TaskModel>>> = flow {
        val response = taskDataSource.getClassroomAll().safeResponse()

        emit(response.toModels().toImmutableList())
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun createTask(
        workspaceId: String,
        title: String,
        description: String,
        dueDate: LocalDateTime,
    ) = flow {
        val response = taskDataSource.createTask(
            workspaceId = workspaceId,
            title = title,
            description = description,
            dueDate = dueDate
        ).safeResponse()

        emit(response)
    }
        .flowOn(dispatcher)
        .asResult()

}