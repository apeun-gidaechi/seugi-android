package com.seugi.data.oauth

import kotlinx.coroutines.flow.Flow
import com.seugi.common.model.Result
import com.seugi.data.core.model.TokenModel

interface OauthRepository {

    suspend fun authenticate(code: String): Flow<Result<TokenModel>>
}