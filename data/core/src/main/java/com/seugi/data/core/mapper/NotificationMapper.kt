package com.seugi.data.core.mapper

import com.seugi.data.core.model.NotificationEmojiModel
import com.seugi.data.core.model.NotificationModel
import com.seugi.network.notification.response.NotificationEmojiResponse
import com.seugi.network.notification.response.NotificationResponse
import kotlinx.collections.immutable.toImmutableList

fun NotificationResponse.toModel() = NotificationModel(
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
fun List<NotificationResponse>.toModels() = this.map {
    it.toModel()
}

fun NotificationEmojiResponse.toModel() = NotificationEmojiModel(
    emoji = emoji,
    userList = userList.toImmutableList(),
)

@JvmName("ListNotificationEmojiResponseToModels")
fun List<NotificationEmojiResponse>.toModels() = this.map {
    it.toModel()
}
