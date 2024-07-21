package com.seugi.network.workspace

import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.ProfileResponse
import com.seugi.network.core.response.Response
import com.seugi.network.workspace.response.CheckWorkspaceResponse

interface WorkspaceDataSource {

    suspend fun checkSchoolCode(schoolCode: String): BaseResponse<CheckWorkspaceResponse>
    suspend fun workspaceApplication(workspaceId: String, workspaceCode: String, role: String): Response
    suspend fun getMembers(workspaceId: String): BaseResponse<List<ProfileResponse>>
}
