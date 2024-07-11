package com.apeun.gidaechi.notification.model

import com.apeun.gidaechi.data.notice.model.NoticeModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class NotificationUiState(
    val isRefresh: Boolean = false,
    val notices: ImmutableList<NoticeModel> = persistentListOf()
)