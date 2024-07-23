package com.seugi.network.notification

import com.seugi.network.core.response.BaseResponse
import com.seugi.network.notification.response.NoticeResponse

interface NotificationDataSource {
    suspend fun getNotices(workspaceId: String): BaseResponse<List<NoticeResponse>>
}
