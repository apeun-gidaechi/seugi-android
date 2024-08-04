package com.seugi.network.token

import com.seugi.network.core.response.BaseResponse

interface TokenDatasource {

    suspend fun refreshToken(refreshToken: String): String
}