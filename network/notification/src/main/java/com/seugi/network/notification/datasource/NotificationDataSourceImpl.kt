package com.seugi.network.notification.datasource

import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.notification.NotificationDataSource
import com.seugi.network.notification.response.NotificationResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class NotificationDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : NotificationDataSource {
    override suspend fun getNotices(workspaceId: String, page: Int, size: Int): BaseResponse<List<NotificationResponse>> =
        httpClient.get("${SeugiUrl.Notification.ROOT}/$workspaceId") {
            parameter("page", page)
            parameter("size", size)
        }.body()
}
