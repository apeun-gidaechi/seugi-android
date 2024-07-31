package com.seugi.data.token.repository

import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.token.TokenRepository
import com.seugi.data.token.mapper.toModel
import com.seugi.data.token.model.TokenModel
import com.seugi.local.room.dao.TokenDao
import com.seugi.local.room.model.TokenEntity
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TokenRepositoryImpl @Inject constructor(
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
    private val tokenDao: TokenDao,
) : TokenRepository {
    override suspend fun insertToken(accessToken: String, refreshToken: String) {
        tokenDao.insert(
            TokenEntity(
                token = accessToken,
                refreshToken = refreshToken,
            ),
        )
    }

    override suspend fun getToken(): Flow<Result<TokenModel>> {
        return flow {
            val tokenEntity = tokenDao.getToken()
            emit(tokenEntity.toModel())
        }
            .flowOn(dispatcher)
            .asResult()
    }
}
