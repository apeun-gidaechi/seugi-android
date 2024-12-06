package com.seugi.data.assignment.model

import kotlinx.datetime.LocalDateTime

data class AssignmentModel(
    val id: Long,
    val workspaceId: String?,
    val title: String,
    val type: AssignmentType,
    val link: String?,
    val description: String?,
    val dueDate: LocalDateTime?,
)
