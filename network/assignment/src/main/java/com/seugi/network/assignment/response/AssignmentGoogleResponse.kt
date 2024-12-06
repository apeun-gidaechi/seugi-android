package com.seugi.network.assignment.response

import java.time.LocalDateTime

data class AssignmentGoogleResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val link: String,
    val dueDate: LocalDateTime?,
)
