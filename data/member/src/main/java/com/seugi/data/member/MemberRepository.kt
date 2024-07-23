package com.seugi.data.member

import com.seugi.common.model.Result
import com.seugi.data.member.model.EmailSignInModel
import com.seugi.network.request.EmailSignInRequest
import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    suspend fun emailSignIn(body: EmailSignInRequest): Flow<Result<EmailSignInModel>>
    suspend fun getCode(email: String): Flow<Result<Int>>

    suspend fun emailSignUp(name: String, email: String, password: String, code: String): Flow<Result<Int>>
}
