package com.seugi.network.notification.request

data class NotificationEditRequest(
    val title: String,
    val content: String,
    val id: Long,
)
