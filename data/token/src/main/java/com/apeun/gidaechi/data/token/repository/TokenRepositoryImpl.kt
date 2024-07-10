package com.apeun.gidaechi.data.token.repository

import android.util.Log
import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.common.model.asResult
import com.apeun.gidaechi.common.utiles.DispatcherType
import com.apeun.gidaechi.common.utiles.SeugiDispatcher
import com.apeun.gidaechi.data.token.TokenRepository
import com.apeun.gidaechi.data.token.mapper.toModel
import com.apeun.gidaechi.data.token.model.TokenModel
import com.apeun.gidaechi.local.room.dao.TokenDao
import com.apeun.gidaechi.local.room.model.TokenEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
    private val tokenDao: TokenDao
): TokenRepository {
    override suspend fun insertToken(accessToken: String, refreshToken: String) {
        tokenDao.insert(TokenEntity(
            token = accessToken,
            refreshToken = refreshToken
        ))
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