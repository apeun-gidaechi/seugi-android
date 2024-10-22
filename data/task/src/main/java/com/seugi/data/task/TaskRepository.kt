package com.seugi.data.task

import com.seugi.common.model.Result
import com.seugi.data.task.model.TaskModel
import java.time.LocalDateTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun getWorkspaceTaskAll(workspaceId: String): Flow<Result<ImmutableList<TaskModel>>>

    suspend fun getGoogleTaskAll(): Flow<Result<ImmutableList<TaskModel>>>

    suspend fun createTask(workspaceId: String, title: String, description: String, dueDate: LocalDateTime): Flow<Result<Boolean>>
}
