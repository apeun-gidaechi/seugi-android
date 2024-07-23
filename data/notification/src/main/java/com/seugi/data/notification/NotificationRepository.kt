package com.seugi.data.notification

import com.seugi.common.model.Result
import com.seugi.data.notification.model.NoticeModel
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    suspend fun getNotices(workspaceId: String): Flow<Result<List<NoticeModel>>>
}
