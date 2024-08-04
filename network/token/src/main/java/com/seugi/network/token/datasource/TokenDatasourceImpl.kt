package com.seugi.network.token.datasource

import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.safeResponse
import com.seugi.network.core.utiles.removeBearer
import com.seugi.network.token.TokenDatasource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class TokenDatasourceImpl @Inject constructor(
    private val httpClient: HttpClient
): TokenDatasource {
    override suspend fun refreshToken(refreshToken: String): String =
        httpClient.get(SeugiUrl.Member.ROOT) {
            parameter("token", removeBearer(refreshToken))
        }.body<BaseResponse<String>>().safeResponse()

}