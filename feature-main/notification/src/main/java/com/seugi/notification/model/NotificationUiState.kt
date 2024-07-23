package com.seugi.notification.model

import com.seugi.data.notification.model.NoticeModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class NotificationUiState(
    val isRefresh: Boolean = false,
    val notices: ImmutableList<NoticeModel> = persistentListOf(),
)
