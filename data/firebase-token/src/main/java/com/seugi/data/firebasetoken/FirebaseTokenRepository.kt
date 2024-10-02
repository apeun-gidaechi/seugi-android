package com.seugi.data.firebasetoken

import com.seugi.common.model.Result
import com.seugi.data.firebasetoken.model.FirebaseTokenModel
import kotlinx.coroutines.flow.Flow

interface FirebaseTokenRepository {
    suspend fun insertToken(firebaseToken: String)

    suspend fun getToken(): Flow<Result<FirebaseTokenModel>>

    suspend fun deleteToken(): Flow<Result<Boolean>>
}
