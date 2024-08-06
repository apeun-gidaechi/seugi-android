package com.seugi.network.profile.datasource

import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.ProfileResponse
import com.seugi.network.profile.ProfileDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : ProfileDataSource {
    override suspend fun loadUserInfo(workspaceId: String): BaseResponse<ProfileResponse> = httpClient.get(SeugiUrl.Profile.ME) {
        parameter("workspaceId", workspaceId)
    }.body()
}
