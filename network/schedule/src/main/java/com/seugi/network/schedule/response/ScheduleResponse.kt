package com.seugi.network.schedule.response

data class ScheduleResponse(
    val id: Long,
    val workspaceId: String,
    val date: String,
    val eventName: String,
    val eventContent: String,
    val grade: List<Int>,
)
