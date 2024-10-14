package com.seugi.data.schedule.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalDate

data class ScheduleModel(
    val id: Long,
    val workspaceId: String,
    val date: LocalDate,
    val eventName: String,
    val eventContent: String,
    val grade: ImmutableList<Int>,
)
