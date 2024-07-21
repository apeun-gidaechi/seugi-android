package com.seugi.network.notice.response

import java.time.LocalDateTime

data class NoticeResponse(
    val id: Long,
    val workspaceId: String,
    val userName: String,
    val title: String,
    val content: String,
    val emoji: List<String>,
    val creationDate: LocalDateTime,
    val lastModifiedDate: LocalDateTime,
)
