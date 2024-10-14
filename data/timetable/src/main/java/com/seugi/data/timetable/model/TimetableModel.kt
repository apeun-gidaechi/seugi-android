package com.seugi.data.timetable.model

data class TimetableModel(
    val id: Long,
    val workspaceId: String,
    val grade: String,
    val classNum: String,
    val time: String,
    val subject: String,
    val date: String,
)
