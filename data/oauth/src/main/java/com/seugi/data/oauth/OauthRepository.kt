package com.seugi.data.oauth

import kotlinx.coroutines.flow.Flow
import com.seugi.common.model.Result

interface OauthRepository {

    suspend fun authenticate(code: String): Flow<Result<TokenModel>>
}