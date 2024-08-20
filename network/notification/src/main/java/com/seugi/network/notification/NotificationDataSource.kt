package com.seugi.network.notification

import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.Response
import com.seugi.network.notification.response.NotificationResponse
import kotlinx.coroutines.flow.Flow

interface NotificationDataSource {

    suspend fun getNotices(workspaceId: String, page: Int, size: Int): BaseResponse<List<NotificationResponse>>

    suspend fun pathEmoji(emoji: String, notificationId: Long): Response

}
