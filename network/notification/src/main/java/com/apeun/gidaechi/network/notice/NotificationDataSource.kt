package com.seugi.network.notice

import com.seugi.network.core.response.BaseResponse
import com.seugi.network.notice.response.NoticeResponse

interface NoticeDataSource {
    suspend fun getNotices(workspaceId: String): BaseResponse<List<NoticeResponse>>
}
