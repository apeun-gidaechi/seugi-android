package com.seugi.network.schedule.response

import kotlinx.datetime.LocalDate

data class ScheduleResponse(
    val id: Long,
    val workspaceId: String,
    val date: LocalDate,
    val eventName: String,
    val eventContent: String,
    val grade: List<Int>
)