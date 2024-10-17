package com.seugi.data.timetable.mapper

import com.seugi.data.timetable.model.TimetableModel
import com.seugi.network.timetable.response.TimetableResponse

fun TimetableResponse.toModel() = TimetableModel(
    id = id,
    workspaceId = workspaceId,
    grade = grade,
    classNum = classNum,
    time = time,
    subject = subject,
    date = date,
)

fun List<TimetableResponse>.toModels() = this.map {
    it.toModel()
}
