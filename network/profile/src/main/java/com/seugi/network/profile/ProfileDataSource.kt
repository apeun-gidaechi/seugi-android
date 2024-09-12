package com.seugi.network.profile

import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.ProfileResponse
import com.seugi.network.core.response.Response

interface ProfileDataSource {
    suspend fun loadUserInfo(workspaceId: String): BaseResponse<ProfileResponse>

    suspend fun patchProfile(workspaceId: String, nick: String, status: String, spot: String, belong: String, phone: String, wire: String, location: String): Response
}
