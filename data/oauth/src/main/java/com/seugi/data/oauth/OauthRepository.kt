package com.seugi.data.oauth

import com.seugi.common.model.Result
import com.seugi.data.core.model.TokenModel
import kotlinx.coroutines.flow.Flow

interface OauthRepository {

    suspend fun authenticate(code: String, fcmToken: String): Flow<Result<TokenModel>>
}
