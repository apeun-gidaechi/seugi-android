package com.apeun.gidaechi.network.notice

import com.apeun.gidaechi.network.core.response.BaseResponse
import com.apeun.gidaechi.network.notice.response.NoticeResponse
import javax.inject.Inject

interface NoticeDataSource {
    suspend fun getNotices(workspaceId: String): BaseResponse<List<NoticeResponse>>
}