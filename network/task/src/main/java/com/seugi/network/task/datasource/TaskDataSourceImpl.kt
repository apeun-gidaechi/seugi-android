package com.seugi.network.task.datasource

import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.Response
import com.seugi.network.task.TaskDataSource
import com.seugi.network.task.request.TaskCreateRequest
import com.seugi.network.task.response.TaskResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import java.time.LocalDateTime
import javax.inject.Inject

class TaskDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient
): TaskDataSource {
    override suspend fun getWorkspaceTaskAll(workspaceId: String): BaseResponse<List<TaskResponse>> =
        httpClient.get("${SeugiUrl.TASK}/${workspaceId}").body()

    override suspend fun createTask(
        workspaceId: String,
        title: String,
        description: String,
        dueDate: LocalDateTime,
    ): Response = httpClient.post(SeugiUrl.TASK) {
            setBody(
                TaskCreateRequest(
                    workspaceId = workspaceId,
                    title = title,
                    description = description,
                    dueDate = dueDate
                )
            )
        }.body()
}