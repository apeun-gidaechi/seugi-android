package com.seugi.data.notification.mapper

import com.seugi.data.notification.model.NotificationEmojiModel
import com.seugi.data.notification.model.NotificationModel
import com.seugi.network.notification.response.NotificationEmojiResponse
import com.seugi.network.notification.response.NotificationResponse
import kotlinx.collections.immutable.toImmutableList

internal fun NotificationResponse.toModel() = NotificationModel(
    id = id,
    workspaceId = workspaceId,
    userId = userId,
    userName = userName,
    title = title,
    content = content,
    emoji = emoji.toModels().toImmutableList(),
    creationDate = creationDate,
    lastModifiedDate = lastModifiedDate,
)

@JvmName("ListNotificationResponseToModels")
internal fun List<NotificationResponse>.toModels() = this.map {
    it.toModel()
}

internal fun NotificationEmojiResponse.toModel() = NotificationEmojiModel(
    emoji = emoji,
    userId = userId,
)

@JvmName("ListNotificationEmojiResponseToModels")
internal fun List<NotificationEmojiResponse>.toModels() = this.map {
    it.toModel()
}
