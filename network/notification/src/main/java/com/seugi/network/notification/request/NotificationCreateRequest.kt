package com.seugi.network.notification.request

data class NotificationCreateRequest(
    val title: String,
    val content: String,
    val workspaceId: String,
)
