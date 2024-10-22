package com.seugi.network.task.response

import java.time.LocalDateTime

data class TaskGoogleResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val link: String,
    val dueDate: LocalDateTime?,
)
