package com.seugi.data.core.model

import kotlinx.collections.immutable.ImmutableList

data class NotificationEmojiModel(
    val emoji: String,
    val userList: ImmutableList<Long>,
)
