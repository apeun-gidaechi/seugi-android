package com.apeun.gidaechi.data.token

import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.data.token.model.TokenModel
import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    suspend fun insertToken(accessToken: String, refreshToken: String)

    suspend fun getToken(): Flow<Result<TokenModel>>
}
