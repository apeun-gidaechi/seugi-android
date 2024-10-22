package com.seugi.data.task.model

import java.time.LocalDateTime

data class TaskModel(
    val id: Long,
    val workspace: String,
    val title: String,
    val description: String,
    val dueDate: LocalDateTime
)