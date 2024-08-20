package com.seugi.data.notification

import com.seugi.common.model.Result
import com.seugi.data.notification.model.NotificationModel
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    suspend fun createNotification(workspaceId: String, title: String, content: String): Flow<Result<Boolean>>

    suspend fun getNotices(workspaceId: String, page: Int, size: Int): Flow<Result<List<NotificationModel>>>

    suspend fun pathEmoji(emoji: String, notificationId: Long): Flow<Result<Boolean>>
}
