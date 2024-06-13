package com.apeun.gidaechi.network

import com.apeun.gidaechi.network.core.response.BaseResponse
import com.apeun.gidaechi.network.core.response.Response
import com.apeun.gidaechi.network.request.EmailSignInRequest
import com.apeun.gidaechi.network.request.EmailSignUpReqest
import com.apeun.gidaechi.network.response.EmailSignInResponse

interface MemberDatasource {
    suspend fun emailSignIn(body: EmailSignInRequest): BaseResponse<EmailSignInResponse>
    suspend fun emailSignUp(body: EmailSignUpReqest): Response
}
