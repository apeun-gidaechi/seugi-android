package com.apeun.gidaechi.network.profile

import com.apeun.gidaechi.network.core.response.BaseResponse
import com.apeun.gidaechi.network.profile.response.ProfileResponse

interface ProfileDataSource {
    suspend fun loadUserInfo(workspaceId: String): BaseResponse<ProfileResponse>
}
