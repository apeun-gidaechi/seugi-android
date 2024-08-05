package com.seugi.network.token

interface TokenDatasource {

    suspend fun refreshToken(refreshToken: String): String
}
