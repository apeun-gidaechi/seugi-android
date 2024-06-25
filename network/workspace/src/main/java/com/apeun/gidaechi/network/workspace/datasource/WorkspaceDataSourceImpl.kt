package com.apeun.gidaechi.network.workspace.datasource

import com.apeun.gidaechi.network.core.SeugiUrl
import com.apeun.gidaechi.network.core.Test
import com.apeun.gidaechi.network.core.response.BaseResponse
import com.apeun.gidaechi.network.core.response.ProfileResponse
import com.apeun.gidaechi.network.core.response.Response
import com.apeun.gidaechi.network.core.utiles.addTestHeader
import com.apeun.gidaechi.network.workspace.WorkspaceDataSource
import com.apeun.gidaechi.network.workspace.request.WorkspaceApplicationRequest
import com.apeun.gidaechi.network.workspace.response.CheckWorkspaceResponse
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

    override suspend fun getMembers(workspaceId: String): BaseResponse<List<ProfileResponse>> =
        httpClient.get(SeugiUrl.Workspace.MEMBERS) {
        parameter("workspaceId", workspaceId)
        addTestHeader(Test.TEST_TOKEN)
    }.body()
}
