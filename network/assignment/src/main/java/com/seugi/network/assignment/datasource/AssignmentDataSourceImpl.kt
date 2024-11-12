package com.seugi.network.assignment.datasource

import com.seugi.network.assignment.AssignmentDataSource
import com.seugi.network.assignment.request.AssignmentCreateRequest
import com.seugi.network.assignment.response.AssignmentGoogleResponse
import com.seugi.network.assignment.response.AssignmentResponse
import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.Response
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import java.time.LocalDateTime
import javax.inject.Inject

class AssignmentDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : AssignmentDataSource {
    override suspend fun getWorkspaceTaskAll(workspaceId: String): BaseResponse<List<AssignmentResponse>> = httpClient.get("${SeugiUrl.TASK}/$workspaceId").body()

    override suspend fun getClassroomAll(): BaseResponse<List<AssignmentGoogleResponse>> = httpClient.get(SeugiUrl.Task.CLASSROOM).body()

    override suspend fun createTask(workspaceId: String, title: String, description: String, dueDate: LocalDateTime): Response = httpClient.post(SeugiUrl.TASK) {
        setBody(
            AssignmentCreateRequest(
                workspaceId = workspaceId,
                title = title,
                description = description,
                dueDate = dueDate,
            ),
        )
    }.body()
}
