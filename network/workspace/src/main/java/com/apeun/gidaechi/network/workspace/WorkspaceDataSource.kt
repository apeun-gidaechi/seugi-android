package com.apeun.gidaechi.network.workspace

import com.apeun.gidaechi.network.core.response.BaseResponse
import com.apeun.gidaechi.network.core.response.ProfileResponse
import com.apeun.gidaechi.network.core.response.Response
import com.apeun.gidaechi.network.workspace.response.CheckWorkspaceResponse

interface WorkspaceDataSource {

    suspend fun checkSchoolCode(schoolCode: String): BaseResponse<CheckWorkspaceResponse>
    suspend fun workspaceApplication(workspaceId: String, workspaceCode: String, role: String): Response
    suspend fun getMembers(workspaceId: String): BaseResponse<List<ProfileResponse>>
}
