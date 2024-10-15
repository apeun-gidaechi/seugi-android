package com.seugi.data.member

import com.seugi.common.model.Result
import com.seugi.data.core.model.TokenModel
import com.seugi.network.core.response.Response
import com.seugi.network.request.EmailSignInRequest
import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    suspend fun emailSignIn(body: EmailSignInRequest): Flow<Result<TokenModel>>
    suspend fun getCode(email: String): Flow<Result<Int>>
    suspend fun emailSignUp(name: String, email: String, password: String, code: String): Flow<Result<Int>>
    suspend fun remove(): Flow<Result<Boolean>>
    suspend fun editProfile(name: String, picture: String, birth: String): Flow<Result<Boolean>>
}
