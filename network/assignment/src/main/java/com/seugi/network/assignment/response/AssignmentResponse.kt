package com.seugi.network.assignment.response

import java.time.LocalDateTime

data class AssignmentResponse(
    val id: Long,
    val workspaceId: String,
    val title: String,
    val description: String?,
    val dueDate: LocalDateTime?,
)
