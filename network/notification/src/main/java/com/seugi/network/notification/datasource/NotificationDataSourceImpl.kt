package com.seugi.network.notification.datasource

import com.seugi.common.model.Result
import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.Response
import com.seugi.network.notification.NotificationDataSource
import com.seugi.network.notification.request.NotificationCreateRequest
import com.seugi.network.notification.request.NotificationEmojiRequest
import com.seugi.network.notification.response.NotificationResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : NotificationDataSource {
    override suspend fun createNotification(
        workspaceId: String,
        title: String,
        content: String,
    ): Response = httpClient.post(SeugiUrl.Notification.ROOT) {
        setBody(
            NotificationCreateRequest(
                workspaceId = workspaceId,
                title = title,
                content = content
            )
        )
    }.body()

    override suspend fun getNotices(workspaceId: String, page: Int, size: Int): BaseResponse<List<NotificationResponse>> =
        httpClient.get("${SeugiUrl.Notification.ROOT}/$workspaceId") {
            parameter("page", page)
            parameter("size", size)
        }.body()

    override suspend fun pathEmoji(emoji: String, notificationId: Long): Response =
        httpClient.patch(SeugiUrl.Notification.EMOJI) {
            setBody(
                NotificationEmojiRequest(
                    emoji = emoji,
                    notificationId = notificationId
                )
            )
        }.body()
}
