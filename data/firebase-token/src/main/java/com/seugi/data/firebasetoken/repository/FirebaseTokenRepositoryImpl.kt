package com.seugi.data.firebasetoken.repository

import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.firebasetoken.FirebaseTokenRepository
import com.seugi.data.firebasetoken.mapper.toModel
import com.seugi.data.firebasetoken.model.FirebaseTokenModel
import com.seugi.local.room.dao.FirebaseTokenDao
import com.seugi.local.room.model.FirebaseTokenEntity
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FirebaseTokenRepositoryImpl @Inject constructor(
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
    private val firebaseTokenDao: FirebaseTokenDao,
) : FirebaseTokenRepository {
    override suspend fun insertToken(firebaseToken: String) {
        val firebaseEntity = FirebaseTokenEntity(firebaseToken = firebaseToken)
        firebaseTokenDao.insert(
            firebaseEntity,
        )
    }

    override suspend fun getToken(): Flow<Result<FirebaseTokenModel>> = flow {
        val tokenEntity = firebaseTokenDao.getToken()
        emit(tokenEntity.toModel())
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun deleteToken(): Flow<Result<Boolean>> = flow {
        firebaseTokenDao.deleteToken()
        emit(true)
    }
        .flowOn(dispatcher)
        .asResult()
}
