package com.seugi.network.notification

import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.Response
import com.seugi.network.notification.response.NotificationResponse

interface NotificationDataSource {

    suspend fun createNotification(workspaceId: String, title: String, content: String): Response

    suspend fun getNotices(workspaceId: String, page: Int, size: Int): BaseResponse<List<NotificationResponse>>

    suspend fun pathEmoji(emoji: String, notificationId: Long): Response

    suspend fun patchNotice(title: String, content: String, notificationId: Long): Response

    suspend fun deleteNotice(workspaceId: String, notificationId: Long): Response
}
