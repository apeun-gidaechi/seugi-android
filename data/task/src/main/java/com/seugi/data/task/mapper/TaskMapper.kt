package com.seugi.data.task.mapper

import com.seugi.data.task.model.TaskModel
import com.seugi.network.task.response.TaskResponse
import kotlinx.datetime.toKotlinLocalDateTime

internal fun TaskResponse.toModel() =
    TaskModel(
        id = id,
        workspaceId = workspaceId,
        title = title,
        description = description,
        dueDate = dueDate?.toKotlinLocalDateTime()
    )

internal fun List<TaskResponse>.toModels() =
    this.map {
        it.toModel()
    }