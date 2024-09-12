package com.seugi.data.notification.model

import kotlinx.collections.immutable.ImmutableList

data class NotificationEmojiModel(
    val emoji: String,
    val userList: ImmutableList<Long>,
)
