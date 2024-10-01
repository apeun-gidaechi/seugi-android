package com.seugi.network.oauth

import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.TokenResponse


interface OauthDatasource {

    suspend fun authenticate(code: String): BaseResponse<TokenResponse>
}