package com.seugi.network.workspace

import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.ProfileResponse
import com.seugi.network.core.response.Response
import com.seugi.network.core.response.WorkspacePermissionResponse
import com.seugi.network.workspace.response.CheckWorkspaceResponse
import com.seugi.network.workspace.response.RetrieveMemberResponse
import com.seugi.network.workspace.response.WaitWorkspaceResponse
import com.seugi.network.workspace.response.WorkspaceResponse

interface WorkspaceDataSource {

    suspend fun checkSchoolCode(schoolCode: String): BaseResponse<CheckWorkspaceResponse>
    suspend fun workspaceApplication(workspaceId: String, workspaceCode: String, role: String): Response
    suspend fun getMembers(workspaceId: String): BaseResponse<List<ProfileResponse>>
    suspend fun getPermission(workspaceId: String): BaseResponse<WorkspacePermissionResponse>

    suspend fun getMyWorkspaces(): BaseResponse<List<WorkspaceResponse>>
    suspend fun getWaitWorkspace(): BaseResponse<List<WaitWorkspaceResponse>>
    suspend fun getWorkspaceData(workspaceId: String): BaseResponse<WorkspaceResponse>

    suspend fun createWorkspace(workspaceName: String, workspaceImage: String): BaseResponse<String>
    suspend fun getWaitMembers(workspaceId: String, role: String): BaseResponse<List<RetrieveMemberResponse>>
    suspend fun getWorkspaceCode(workspaceId: String): BaseResponse<String>
    suspend fun addMember(workspaceId: String, userSet: List<Long>, role: String): Response
    suspend fun cancelMember(workspaceId: String, userSet: List<Long>, role: String): Response
}
