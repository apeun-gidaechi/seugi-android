package com.seugi.data.assignment.mapper

import com.seugi.data.assignment.model.AssignmentModel
import com.seugi.data.assignment.model.AssignmentType
import com.seugi.network.assignment.response.AssignmentGoogleResponse
import com.seugi.network.assignment.response.AssignmentResponse
import kotlinx.datetime.toKotlinLocalDateTime

internal fun AssignmentResponse.toModel() = AssignmentModel(
    id = id,
    workspaceId = workspaceId,
    title = title,
    description = description,
    dueDate = dueDate?.toKotlinLocalDateTime(),
    type = AssignmentType.WORKSPACE,
    link = null,
)

internal fun AssignmentGoogleResponse.toModel() = AssignmentModel(
    id = id,
    workspaceId = null,
    title = title,
    description = description,
    dueDate = dueDate?.toKotlinLocalDateTime(),
    type = AssignmentType.GOOGLE,
    link = link,
)

@JvmName("ListTaskResponseToModels")
internal fun List<AssignmentResponse>.toModels() = this.map {
    it.toModel()
}

@JvmName("ListTaskGoogleResponseToModels")
internal fun List<AssignmentGoogleResponse>.toModels() = this.map {
    it.toModel()
}
