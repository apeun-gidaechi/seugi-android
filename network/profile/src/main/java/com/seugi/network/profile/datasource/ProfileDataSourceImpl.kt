package com.seugi.network.profile.datasource

import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.ProfileResponse
import com.seugi.network.core.response.Response
import com.seugi.network.profile.ProfileDataSource
import com.seugi.network.profile.request.ProfileRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : ProfileDataSource {
    override suspend fun loadUserInfo(workspaceId: String): BaseResponse<ProfileResponse> = httpClient.get(SeugiUrl.Profile.ME) {
        parameter("workspaceId", workspaceId)
    }.body()

    override suspend fun patchProfile(
        workspaceId: String,
        nick: String,
        status: String,
        spot: String,
        belong: String,
        phone: String,
        wire: String,
        location: String,
    ): Response = httpClient.patch("${SeugiUrl.PROFILE}/${workspaceId}") {
        setBody(
            ProfileRequest(
                nick = nick,
                status = status,
                spot = spot,
                belong = belong,
                phone = phone,
                wire = wire,
                location = location
            )
        )
        contentType(ContentType.Application.Json)
    }.body()
}
