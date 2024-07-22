package com.seugi.data.notice.repository

import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.notice.NoticeRepository
import com.seugi.data.notice.mapper.toModels
import com.seugi.data.notice.model.NoticeModel
import com.seugi.network.core.response.safeResponse
import com.seugi.network.notice.NoticeDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NoticeRepositoryImpl @Inject constructor(
    private val dataSource: NoticeDataSource,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : NoticeRepository {
    override suspend fun getNotices(workspaceId: String): Flow<Result<List<NoticeModel>>> = flow {
        val response = dataSource.getNotices(workspaceId).safeResponse()

        emit(response.toModels())
    }
        .flowOn(dispatcher)
        .asResult()
}
