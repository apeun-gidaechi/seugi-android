package com.seugi.network

import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.Response
import com.seugi.network.request.EmailSignInRequest
import com.seugi.network.response.EmailSignInResponse

interface MemberDatasource {
    suspend fun emailSignIn(body: EmailSignInRequest): BaseResponse<EmailSignInResponse>

    suspend fun getCode(email: String): Response
    suspend fun emailSignUp(name: String, email: String, password: String, code: String): Response
}
