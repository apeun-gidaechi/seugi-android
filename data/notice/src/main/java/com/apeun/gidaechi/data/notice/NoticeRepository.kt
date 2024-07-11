package com.apeun.gidaechi.data.notice

import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.data.notice.model.NoticeModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface NoticeRepository {

    suspend fun getNotices(workspaceId: String): Flow<Result<List<NoticeModel>>>
}