package com.seugi.data.notice.mapper

import com.seugi.data.notice.model.NoticeModel
import com.seugi.network.notice.response.NoticeResponse
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
