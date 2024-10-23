package com.seugi.data.core.mapper

import com.seugi.data.core.model.TimetableModel
import com.seugi.network.timetable.response.TimetableResponse
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.toJavaLocalDate

fun TimetableResponse.toModel() = TimetableModel(
    id = id,
    workspaceId = workspaceId,
    grade = grade,
    classNum = classNum,
    time = time,
    subject = subject,
    date = date,
    weekday = when (date.toJavaLocalDate().dayOfWeek) {
        java.time.DayOfWeek.MONDAY -> "월"
        java.time.DayOfWeek.TUESDAY -> "화"
        java.time.DayOfWeek.WEDNESDAY -> "수"
        java.time.DayOfWeek.THURSDAY -> "목"
        java.time.DayOfWeek.FRIDAY -> "금"
        java.time.DayOfWeek.SATURDAY -> "토"
        java.time.DayOfWeek.SUNDAY -> "일"
    },
)

fun List<TimetableResponse>.toModels() = this.map {
    it.toModel()
}
