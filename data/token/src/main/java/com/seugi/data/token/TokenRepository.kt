package com.seugi.data.token

import com.seugi.common.model.Result
import com.seugi.data.token.model.TokenModel
import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    suspend fun insertToken(accessToken: String, refreshToken: String)

    suspend fun getToken(): Flow<Result<TokenModel>>

    suspend fun newToken(): Flow<Result<TokenModel>>
}
