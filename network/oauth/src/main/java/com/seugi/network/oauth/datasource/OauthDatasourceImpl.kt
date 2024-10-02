package com.seugi.network.oauth.datasource

import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.TokenResponse
import com.seugi.network.oauth.OauthDatasource
import com.seugi.network.oauth.request.OauthRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class OauthDatasourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : OauthDatasource {

    override suspend fun authenticate(code: String, fcmToken: String): BaseResponse<TokenResponse> = httpClient.post(SeugiUrl.Oauth.GOOGLE_AUTHENTICATE) {
        setBody(
            body = OauthRequest(
                code = code,
                platform = "ANDROID",
                token = fcmToken,
            ),
        )
    }.body()
}
