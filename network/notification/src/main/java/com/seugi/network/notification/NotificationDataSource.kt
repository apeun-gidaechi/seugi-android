package com.seugi.network.notification

import com.seugi.network.core.response.BaseResponse
import com.seugi.network.notification.response.NotificationResponse

interface NotificationDataSource {
    suspend fun getNotices(workspaceId: String, page: Int, size: Int): BaseResponse<List<NotificationResponse>>
}
