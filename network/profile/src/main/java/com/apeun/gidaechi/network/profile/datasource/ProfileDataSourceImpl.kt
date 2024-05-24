package com.apeun.gidaechi.network.profile.datasource

import com.apeun.gidaechi.network.core.SeugiUrl
import com.apeun.gidaechi.network.core.Test
import com.apeun.gidaechi.network.core.response.BaseResponse
import com.apeun.gidaechi.network.core.utiles.addTestHeader
import com.apeun.gidaechi.network.profile.ProfileDataSource
import com.apeun.gidaechi.network.profile.response.ProfileResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient
): ProfileDataSource {
    override suspend fun loadUserInfo(workspaceId: String): BaseResponse<ProfileResponse> =
        httpClient.get(SeugiUrl.Profile.ME) {
            parameter("workspaceId", workspaceId)
            addTestHeader(Test.TEST_TOKEN)
        }.body()
}