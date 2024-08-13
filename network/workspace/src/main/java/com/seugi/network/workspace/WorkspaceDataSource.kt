package com.seugi.network.workspace

import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.ProfileResponse
import com.seugi.network.core.response.Response
import com.seugi.network.workspace.response.CheckWorkspaceResponse
import com.seugi.network.workspace.response.WaitWorkspaceResponse
import com.seugi.network.workspace.response.WorkspaceResponse

interface WorkspaceDataSource {

    suspend fun checkSchoolCode(schoolCode: String): BaseResponse<CheckWorkspaceResponse>
    suspend fun workspaceApplication(workspaceId: String, workspaceCode: String, role: String): Response
    suspend fun getMembers(workspaceId: String): BaseResponse<List<ProfileResponse>>

    suspend fun getMyWorkspaces(): BaseResponse<List<WorkspaceResponse>>
    suspend fun getWaitWorkspace(): BaseResponse<List<WaitWorkspaceResponse>>
    suspend fun getWorkspaceData(workspaceId: String): BaseResponse<WorkspaceResponse>
}
