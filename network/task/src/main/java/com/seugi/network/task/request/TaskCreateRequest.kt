package com.seugi.network.task.request

import java.time.LocalDateTime

data class TaskCreateRequest(
    val workspaceId: String,
    val title: String,
    val description: String,
    val dueDate: LocalDateTime,
)