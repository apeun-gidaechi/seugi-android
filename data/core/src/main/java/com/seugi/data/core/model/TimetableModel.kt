package com.seugi.data.core.model

import kotlinx.datetime.LocalDate

data class TimetableModel(
    val id: Long,
    val workspaceId: String,
    val grade: String,
    val classNum: String,
    val time: String,
    val subject: String,
    val date: LocalDate,
    val weekday: String,
)