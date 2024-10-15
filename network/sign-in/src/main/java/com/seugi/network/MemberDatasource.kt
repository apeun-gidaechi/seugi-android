package com.seugi.network

import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.Response
import com.seugi.network.core.response.TokenResponse
import com.seugi.network.request.EmailSignInRequest

interface MemberDatasource {
    suspend fun emailSignIn(body: EmailSignInRequest): BaseResponse<TokenResponse>
    suspend fun getCode(email: String): Response
    suspend fun emailSignUp(name: String, email: String, password: String, code: String): Response
    suspend fun editProfile(name: String, picture: String, birth: String): Response
}
