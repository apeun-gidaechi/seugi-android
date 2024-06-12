package com.apeun.gidaechi.data

import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.data.model.EmailSignInModel
import com.apeun.gidaechi.network.request.EmailSignInRequest
import kotlinx.coroutines.flow.Flow

interface EmailSignInRepository {
    suspend fun emailSignIn(body: EmailSignInRequest): Flow<Result<EmailSignInModel>>
}
