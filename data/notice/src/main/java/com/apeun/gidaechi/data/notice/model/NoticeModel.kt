package com.apeun.gidaechi.data.notice.model

import java.time.LocalDateTime
import kotlinx.collections.immutable.ImmutableList

data class NoticeModel(
    val id: Long,
    val workspaceId: String,
    val userName: String,
    val title: String,
    val content: String,
    val emoji: ImmutableList<String>,
    val creationDate: LocalDateTime,
    val lastModifiedDate: LocalDateTime,
)
