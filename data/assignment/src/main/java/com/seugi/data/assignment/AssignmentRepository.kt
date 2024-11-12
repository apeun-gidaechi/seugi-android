package com.seugi.data.assignment

import com.seugi.common.model.Result
import com.seugi.data.assignment.model.AssignmentModel
import java.time.LocalDateTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface AssignmentRepository {

    suspend fun getWorkspaceTaskAll(workspaceId: String): Flow<Result<ImmutableList<AssignmentModel>>>

    suspend fun getGoogleTaskAll(): Flow<Result<ImmutableList<AssignmentModel>>>

    suspend fun createTask(workspaceId: String, title: String, description: String, dueDate: LocalDateTime): Flow<Result<Boolean>>
}
