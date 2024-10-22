package com.seugi.network.task.response

import java.time.LocalDateTime


data class TaskResponse(
    val id: Long,
    val workspace: String,
    val title: String,
    val description: String,
    val dueDate: LocalDateTime
)