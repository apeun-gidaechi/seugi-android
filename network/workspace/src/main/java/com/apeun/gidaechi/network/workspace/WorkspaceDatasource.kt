package com.apeun.gidaechi.network.workspace

import com.apeun.gidaechi.network.core.response.BaseResponse
import com.apeun.gidaechi.network.workspace.response.CheckWorkspaceResponse

interface WorkspaceDatasource {

    suspend fun checkSchoolCode(schoolCode: String): BaseResponse<CheckWorkspaceResponse>

}