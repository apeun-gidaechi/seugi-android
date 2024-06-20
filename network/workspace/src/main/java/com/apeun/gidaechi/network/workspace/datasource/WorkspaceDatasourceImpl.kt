package com.apeun.gidaechi.network.workspace.datasource

import com.apeun.gidaechi.common.utiles.DispatcherType
import com.apeun.gidaechi.common.utiles.SeugiDispatcher
import com.apeun.gidaechi.network.core.SeugiUrl
import com.apeun.gidaechi.network.core.Test
import com.apeun.gidaechi.network.core.response.BaseResponse
import com.apeun.gidaechi.network.core.utiles.addTestHeader
import com.apeun.gidaechi.network.workspace.WorkspaceDatasource
import com.apeun.gidaechi.network.workspace.response.CheckWorkspaceResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class WorkspaceDatasourceImpl @Inject constructor(
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
    private val httpClient: HttpClient
): WorkspaceDatasource {
    override suspend fun checkSchoolCode(schoolCode: String): BaseResponse<CheckWorkspaceResponse> =
        httpClient.get("${SeugiUrl.Workspace.CHECK_WORKSPACE}$schoolCode"){
            addTestHeader(Test.TEST_TOKEN)
        }.body()

}