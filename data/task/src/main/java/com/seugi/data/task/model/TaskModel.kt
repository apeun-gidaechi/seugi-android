package com.seugi.data.task.model

import kotlinx.datetime.LocalDateTime


data class TaskModel(
    val id: Long,
    val workspaceId: String?,
    val title: String,
    val type: TaskType,
    val link: String?,
    val description: String?,
    val dueDate: LocalDateTime?
)