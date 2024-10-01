package com.seugi.data.oauth.repository

import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.oauth.OauthRepository
import com.seugi.data.oauth.TokenModel
import com.seugi.data.oauth.toModel
import com.seugi.network.core.response.safeResponse
import com.seugi.network.oauth.OauthDatasource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OauthRepositoryImpl @Inject constructor(
    private val oauthDatasource: OauthDatasource,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
): OauthRepository {
    override suspend fun authenticate(code: String): Flow<Result<TokenModel>> = flow{
        val response = oauthDatasource.authenticate(code).safeResponse()
        emit(response.toModel())
    }
        .flowOn(dispatcher)
        .asResult()

}