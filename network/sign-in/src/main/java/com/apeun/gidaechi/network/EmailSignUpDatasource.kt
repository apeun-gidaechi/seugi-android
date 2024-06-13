package com.apeun.gidaechi.network

import com.apeun.gidaechi.network.core.response.Response
import com.apeun.gidaechi.network.request.EmailSignUpReqest

interface EmailSignUpDatasource {
    suspend fun emailSignUp(body: EmailSignUpReqest): Response
}