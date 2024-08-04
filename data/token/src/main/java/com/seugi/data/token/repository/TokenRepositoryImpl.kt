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
import com.seugi.network.token.TokenDatasource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TokenRepositoryImpl @Inject constructor(
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
    private val tokenDao: TokenDao,
    private val tokenDatasource: TokenDatasource
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

    override suspend fun newToken(): Flow<Result<TokenModel>> {
        return flow {
            tokenDao.getToken()?.apply {
                val newTokenEntity = TokenEntity(
                    token = tokenDatasource.refreshToken(refreshToken),
                    refreshToken = refreshToken
                )
                tokenDao.insert(newTokenEntity)
                emit(newTokenEntity.toModel())
            }
        }
            .flowOn(dispatcher)
            .asResult()
    }
}
