package com.seugi.network.notification.datasource

import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.notification.NotificationDataSource
import com.seugi.network.notification.response.NoticeResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class NotificationDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : NotificationDataSource {
    override suspend fun getNotices(workspaceId: String): BaseResponse<List<NoticeResponse>> =
        httpClient.get("${SeugiUrl.Notice.ROOT}/$workspaceId").body()
}
