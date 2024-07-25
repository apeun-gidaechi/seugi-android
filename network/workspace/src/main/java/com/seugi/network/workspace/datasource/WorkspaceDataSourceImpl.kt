package com.seugi.network.workspace.datasource

import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.Test
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.ProfileResponse
import com.seugi.network.core.response.Response
import com.seugi.network.core.utiles.addTestHeader
import com.seugi.network.workspace.WorkspaceDataSource
import com.seugi.network.workspace.request.WorkspaceApplicationRequest
import com.seugi.network.workspace.response.CheckWorkspaceResponse
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
    override suspend fun checkSchoolCode(schoolCode: String): BaseResponse<CheckWorkspaceResponse> =
        httpClient.get("${SeugiUrl.Workspace.CHECK_WORKSPACE}$schoolCode") {
            addTestHeader(Test.TEST_TOKEN)
        }.body()

    override suspend fun workspaceApplication(workspaceId: String, workspaceCode: String, role: String): Response =
        httpClient.post("${SeugiUrl.Workspace.APPLICATION}") {
            addTestHeader(Test.TEST_TOKEN)
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
        addTestHeader(Test.TEST_TOKEN)
    }.body()
}
