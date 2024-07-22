package com.seugi.network.notice.datasource

import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.Test
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.utiles.addTestHeader
import com.seugi.network.notice.NoticeDataSource
import com.seugi.network.notice.response.NoticeResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class NoticeDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : NoticeDataSource {
    override suspend fun getNotices(workspaceId: String): BaseResponse<List<NoticeResponse>> = httpClient.get("${SeugiUrl.Notice.ROOT}/$workspaceId") {
        addTestHeader(Test.TEST_TOKEN)
    }.body()
}
