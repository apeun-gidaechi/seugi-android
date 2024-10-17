package com.seugi.data.schedule.mapper

import com.seugi.data.schedule.model.ScheduleModel
import com.seugi.network.schedule.response.ScheduleResponse
import kotlinx.collections.immutable.toImmutableList

internal fun ScheduleResponse.toModel() = ScheduleModel(
    id = id,
    workspaceId = workspaceId,
    date = date,
    eventName = eventName,
    eventContent = eventContent,
    grade = grade.toImmutableList(),
)

internal fun List<ScheduleResponse>.toModels() = this.map {
    it.toModel()
}
