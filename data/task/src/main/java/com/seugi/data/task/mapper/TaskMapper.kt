package com.seugi.data.task.mapper

import com.seugi.data.task.model.TaskModel
import com.seugi.data.task.model.TaskType
import com.seugi.network.task.response.TaskGoogleResponse
import com.seugi.network.task.response.TaskResponse
import kotlinx.datetime.toKotlinLocalDateTime

internal fun TaskResponse.toModel() = TaskModel(
    id = id,
    workspaceId = workspaceId,
    title = title,
    description = description,
    dueDate = dueDate?.toKotlinLocalDateTime(),
    type = TaskType.WORKSPACE,
    link = null,
)

internal fun TaskGoogleResponse.toModel() = TaskModel(
    id = id,
    workspaceId = null,
    title = title,
    description = description,
    dueDate = dueDate?.toKotlinLocalDateTime(),
    type = TaskType.GOOGLE,
    link = link,
)

@JvmName("ListTaskResponseToModels")
internal fun List<TaskResponse>.toModels() = this.map {
    it.toModel()
}

@JvmName("ListTaskGoogleResponseToModels")
internal fun List<TaskGoogleResponse>.toModels() = this.map {
    it.toModel()
}
