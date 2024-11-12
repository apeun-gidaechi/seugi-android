package com.seugi.network.assignment.request

import java.time.LocalDateTime

data class AssignmentCreateRequest(
    val workspaceId: String,
    val title: String,
    val description: String,
    val dueDate: LocalDateTime,
)
