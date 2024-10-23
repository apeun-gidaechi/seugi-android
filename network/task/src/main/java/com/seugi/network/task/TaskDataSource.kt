package com.seugi.network.task

import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.Response
import com.seugi.network.task.response.TaskGoogleResponse
import com.seugi.network.task.response.TaskResponse
import java.time.LocalDateTime

interface TaskDataSource {

    suspend fun getWorkspaceTaskAll(workspaceId: String): BaseResponse<List<TaskResponse>>

    suspend fun getClassroomAll(): BaseResponse<List<TaskGoogleResponse>>

    suspend fun createTask(workspaceId: String, title: String, description: String, dueDate: LocalDateTime): Response
}
