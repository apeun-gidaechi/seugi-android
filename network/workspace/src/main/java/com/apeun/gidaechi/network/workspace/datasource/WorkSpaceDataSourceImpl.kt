package com.apeun.gidaechi.network.workspace.datasource

import com.apeun.gidaechi.network.core.SeugiUrl
import com.apeun.gidaechi.network.core.Test
import com.apeun.gidaechi.network.core.response.BaseResponse
import com.apeun.gidaechi.network.core.response.ProfileResponse
import com.apeun.gidaechi.network.core.response.UserResponse
import com.apeun.gidaechi.network.core.utiles.addTestHeader
import com.apeun.gidaechi.network.workspace.WorkSpaceDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class WorkSpaceDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient
): WorkSpaceDataSource {
    override suspend fun getMembers(workspaceId: String): BaseResponse<List<ProfileResponse>> =
        httpClient.get(SeugiUrl.WorkSpace.MEMBERS) {
            parameter("workspaceId", workspaceId)
            addTestHeader(Test.TEST_TOKEN)
        }.body()
}