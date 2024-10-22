package com.seugi.data.task.mapper

import com.seugi.data.task.model.TaskModel
import com.seugi.network.task.response.TaskResponse

internal fun TaskResponse.toModel() =
    TaskModel(
        id = id,
        workspace = workspace,
        title = title,
        description = description,
        dueDate = dueDate
    )

internal fun List<TaskResponse>.toModels() =
    this.map {
        it.toModel()
    }