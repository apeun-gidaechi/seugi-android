package com.seugi.data.notification.mapper

import com.seugi.data.notification.model.NoticeModel
import com.seugi.network.notification.response.NoticeResponse
import kotlinx.collections.immutable.toImmutableList

internal fun NoticeResponse.toModel() = NoticeModel(
    id = id,
    workspaceId = workspaceId,
    userName = userName,
    title = title,
    content = content,
    emoji = emoji.toImmutableList(),
    creationDate = creationDate,
    lastModifiedDate = lastModifiedDate,
)

internal fun List<NoticeResponse>.toModels() = this.map {
    it.toModel()
}
