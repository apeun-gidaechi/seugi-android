package com.apeun.gidaechi.data.notice.repository

import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.common.model.asResult
import com.apeun.gidaechi.common.utiles.DispatcherType
import com.apeun.gidaechi.common.utiles.SeugiDispatcher
import com.apeun.gidaechi.data.notice.NoticeRepository
import com.apeun.gidaechi.data.notice.mapper.toModels
import com.apeun.gidaechi.data.notice.model.NoticeModel
import com.apeun.gidaechi.network.core.response.safeResponse
import com.apeun.gidaechi.network.notice.NoticeDataSource
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
