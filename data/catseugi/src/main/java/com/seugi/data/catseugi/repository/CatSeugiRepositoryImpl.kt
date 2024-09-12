package com.seugi.data.catseugi.repository

import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.catseugi.CatSeugiRepository
import com.seugi.network.catseugi.CatSeugiDataSource
import com.seugi.network.core.response.safeResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CatSeugiRepositoryImpl @Inject constructor(
    private val catSeugiDataSource: CatSeugiDataSource,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher
): CatSeugiRepository {
    override suspend fun sendText(text: String): Flow<Result<String>> = flow {
        val response = catSeugiDataSource.sendText(text).safeResponse()

        emit(response)
    }
        .flowOn(dispatcher)
        .asResult()
}