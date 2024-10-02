package com.seugi.data.token

import com.seugi.common.model.Result
import com.seugi.data.core.model.LocalTokenModel
import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    suspend fun insertToken(accessToken: String, refreshToken: String)

    suspend fun getToken(): Flow<Result<LocalTokenModel>>

    suspend fun newToken(): Flow<Result<LocalTokenModel>>

    suspend fun deleteToken(): Flow<Result<Boolean>>
}
