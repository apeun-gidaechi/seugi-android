package com.seugi.network.notification.response

import java.time.LocalDateTime

data class NotificationResponse(
    val id: Long,
    val workspaceId: String,
    val userName: String,
    val title: String,
    val content: String,
    val emoji: List<NotificationEmojiResponse>,
    val creationDate: LocalDateTime,
    val lastModifiedDate: LocalDateTime,
)
