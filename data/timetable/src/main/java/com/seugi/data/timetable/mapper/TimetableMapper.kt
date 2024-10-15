package com.seugi.data.timetable.mapper

import com.seugi.data.timetable.model.TimetableModel
import com.seugi.network.timetable.response.TimetableResponse
import kotlinx.datetime.LocalDate

fun TimetableResponse.toModel() = TimetableModel(
    id = id,
    workspaceId = workspaceId,
    grade = grade,
    classNum = classNum,
    time = time,
    subject = subject,
    date = LocalDate(date.substring(0, 4).toInt(), date.substring(4, 6).toInt(), date.substring(6, 8).toInt()),
)

fun List<TimetableResponse>.toModels() = this.map {
    it.toModel()
}
