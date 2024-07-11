package com.apeun.gidaechi.network.notice.datasource

import com.apeun.gidaechi.network.core.SeugiUrl
import com.apeun.gidaechi.network.core.Test
import com.apeun.gidaechi.network.core.response.BaseResponse
import com.apeun.gidaechi.network.core.utiles.addTestHeader
import com.apeun.gidaechi.network.notice.NoticeDataSource
import com.apeun.gidaechi.network.notice.response.NoticeResponse
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
