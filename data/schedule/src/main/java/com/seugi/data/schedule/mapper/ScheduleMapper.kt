package com.seugi.data.schedule.mapper

import com.seugi.data.schedule.model.ScheduleModel
import com.seugi.network.schedule.response.ScheduleResponse
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDate

internal fun ScheduleResponse.toModel() = ScheduleModel(
    id = id,
    workspaceId = workspaceId,
    date = LocalDate(date.substring(0, 4).toInt(), date.substring(4, 6).toInt(), date.substring(6, 8).toInt()),
    eventName = eventName,
    eventContent = eventContent,
    grade = grade.toImmutableList(),
)

internal fun List<ScheduleResponse>.toModels() = this.map {
    it.toModel()
}
