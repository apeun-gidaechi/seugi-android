package com.seugi.network.timetable.response

import com.seugi.network.core.response.FakeLocalDateResponse
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

data class TimetableResponse(
    val id: Long,
    val workspaceId: String,
    val grade: String,
    val classNum: String,
    val time: String,
    val subject: String,
    val date: String,
)
