package com.seugi.network.assignment

import com.seugi.network.assignment.response.AssignmentGoogleResponse
import com.seugi.network.assignment.response.AssignmentResponse
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.Response
import java.time.LocalDateTime

interface AssignmentDataSource {

    suspend fun getWorkspaceTaskAll(workspaceId: String): BaseResponse<List<AssignmentResponse>>

    suspend fun getClassroomAll(): BaseResponse<List<AssignmentGoogleResponse>>

    suspend fun createTask(workspaceId: String, title: String, description: String, dueDate: LocalDateTime): Response
}
