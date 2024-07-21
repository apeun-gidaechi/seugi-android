package com.seugi.data.notice

import com.seugi.common.model.Result
import com.seugi.data.notice.model.NoticeModel
import kotlinx.coroutines.flow.Flow

interface NoticeRepository {

    suspend fun getNotices(workspaceId: String): Flow<Result<List<NoticeModel>>>
}
