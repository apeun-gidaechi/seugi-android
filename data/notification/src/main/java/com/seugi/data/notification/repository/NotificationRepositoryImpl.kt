package com.seugi.data.notification.repository

import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.core.mapper.toModels
import com.seugi.data.core.model.NotificationModel
import com.seugi.data.notification.NotificationRepository
import com.seugi.network.core.response.safeResponse
import com.seugi.network.notification.NotificationDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NotificationRepositoryImpl @Inject constructor(
    private val dataSource: NotificationDataSource,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : NotificationRepository {
    override suspend fun createNotification(workspaceId: String, title: String, content: String): Flow<Result<Boolean>> = flow {
        val response = dataSource.createNotification(
            workspaceId = workspaceId,
            title = title,
            content = content,
        ).safeResponse()

        emit(response)
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun getNotices(workspaceId: String, page: Int, size: Int): Flow<Result<List<NotificationModel>>> = flow {
        val response = dataSource.getNotices(
            workspaceId = workspaceId,
            page = page,
            size = size,
        ).safeResponse()

        emit(response.toModels())
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun pathEmoji(emoji: String, notificationId: Long): Flow<Result<Boolean>> = flow {
        val response = dataSource.pathEmoji(
            emoji = emoji,
            notificationId = notificationId,
        ).safeResponse()

        emit(response)
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun patchNotice(title: String, content: String, notificationId: Long): Flow<Result<Boolean>> = flow {
        val response = dataSource.patchNotice(
            title = title,
            content = content,
            notificationId = notificationId,
        ).safeResponse()

        emit(response)
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun deleteNotice(workspaceId: String, notificationId: Long): Flow<Result<Boolean>> = flow {
        val response = dataSource.deleteNotice(
            workspaceId = workspaceId,
            notificationId = notificationId,
        ).safeResponse()

        emit(response)
    }
        .flowOn(dispatcher)
        .asResult()
}
