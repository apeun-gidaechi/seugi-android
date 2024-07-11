package com.apeun.gidaechi.data.notice.model

import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDateTime

data class NoticeModel(
    val id: Long,
    val workspaceId: String,
    val userName: String,
    val title: String,
    val content: String,
    val emoji: ImmutableList<String>,
    val creationDate: LocalDateTime,
    val lastModifiedDate: LocalDateTime
)