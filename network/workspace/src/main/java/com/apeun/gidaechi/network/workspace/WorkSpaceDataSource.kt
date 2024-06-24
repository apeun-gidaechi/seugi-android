package com.apeun.gidaechi.network.workspace

import com.apeun.gidaechi.network.core.response.BaseResponse
import com.apeun.gidaechi.network.core.response.ProfileResponse
import com.apeun.gidaechi.network.core.response.UserResponse

interface WorkSpaceDataSource {

    suspend fun getMembers(workspaceId: String): BaseResponse<List<ProfileResponse>>
}