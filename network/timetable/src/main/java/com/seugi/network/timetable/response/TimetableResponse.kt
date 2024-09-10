package com.seugi.network.timetable.response

data class TimetableResponse(
    val id: Long,
    val workspaceId: String,
    val grade: String,
    val classNum: String,
    val time: String,
    val subject: String,
    val date: String,
)
