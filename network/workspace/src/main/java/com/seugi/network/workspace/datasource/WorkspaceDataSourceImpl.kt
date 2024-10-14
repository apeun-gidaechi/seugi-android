package com.seugi.network.workspace.datasource

import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.ProfileResponse
import com.seugi.network.core.response.Response
import com.seugi.network.core.response.WorkspacePermissionResponse
import com.seugi.network.workspace.WorkspaceDataSource
import com.seugi.network.workspace.request.CreateWorkspaceRequest
import com.seugi.network.workspace.request.WorkspaceApplicationRequest
import com.seugi.network.workspace.response.CheckWorkspaceResponse
import com.seugi.network.workspace.response.WaitWorkspaceResponse
import com.seugi.network.workspace.response.WorkspaceResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class WorkspaceDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : WorkspaceDataSource {
    override suspend fun checkSchoolCode(schoolCode: String): BaseResponse<CheckWorkspaceResponse> = httpClient.get("${SeugiUrl.Workspace.CHECK_WORKSPACE}/$schoolCode") {}.body()

    override suspend fun workspaceApplication(workspaceId: String, workspaceCode: String, role: String): Response = httpClient.post(SeugiUrl.Workspace.APPLICATION) {
        setBody(
            body = WorkspaceApplicationRequest(
                workspaceId = workspaceId,
                workspaceCode = workspaceCode,
                role = role,
            ),
        )
    }.body()

    override suspend fun getMembers(workspaceId: String): BaseResponse<List<ProfileResponse>> = httpClient.get(SeugiUrl.Workspace.MEMBERS) {
        parameter("workspaceId", workspaceId)
    }.body()

    override suspend fun getPermission(workspaceId: String): BaseResponse<WorkspacePermissionResponse> = httpClient.get(SeugiUrl.Workspace.PERMISSION) {
        parameter("workspaceId", workspaceId)
    }.body()

    override suspend fun getMyWorkspaces(): BaseResponse<List<WorkspaceResponse>> = httpClient.get("${SeugiUrl.Workspace.GET_MY_WORKSPACES}/") {}.body()

    override suspend fun getWaitWorkspace(): BaseResponse<List<WaitWorkspaceResponse>> = httpClient.get(SeugiUrl.Workspace.GET_MY_WAIT_WORKSPACES) {
    }.body()

    override suspend fun getWorkspaceData(workspaceId: String): BaseResponse<WorkspaceResponse> = httpClient.get("${SeugiUrl.WORKSPACE}/$workspaceId") {
    }.body()

    override suspend fun createWorkspace(workspaceName: String, workspaceImage: String): BaseResponse<String> = httpClient.post("${SeugiUrl.WORKSPACE}/") {
        setBody(
            CreateWorkspaceRequest(
                workspaceName = workspaceName,
                workspaceImageUrl = workspaceImage,
            ),
        )
    }.body()
}
