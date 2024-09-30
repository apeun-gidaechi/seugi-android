package com.seugi.data.firebase_token

import com.seugi.common.model.Result
import com.seugi.data.firebase_token.model.FirebaseTokenModel
import kotlinx.coroutines.flow.Flow

interface FirebaseTokenRepository {
    suspend fun insertToken(firebaseToken: String)

    suspend fun getToken(): Flow<Result<FirebaseTokenModel>>

    suspend fun deleteToken(): Flow<Result<Boolean>>
}