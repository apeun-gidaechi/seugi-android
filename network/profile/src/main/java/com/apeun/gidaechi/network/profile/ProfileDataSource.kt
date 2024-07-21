package com.seugi.network.profile

import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.ProfileResponse

interface ProfileDataSource {
    suspend fun loadUserInfo(workspaceId: String): BaseResponse<ProfileResponse>
}
