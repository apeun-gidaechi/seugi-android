package com.apeun.gidaechi.network

import com.apeun.gidaechi.network.core.response.BaseResponse
import com.apeun.gidaechi.network.request.EmailSignInRequest
import com.apeun.gidaechi.network.response.EmailSignInResponse

interface EmailSignInDatasource {
    suspend fun emailSignIn(body: EmailSignInRequest): BaseResponse<EmailSignInResponse>
}
