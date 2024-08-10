package com.seugi.notification.model

import androidx.compose.ui.util.fastForEach
import com.seugi.data.notification.model.NotificationModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class NotificationUiState(
    val isRefresh: Boolean = false,
    val notices: ImmutableList<NotificationModel> = persistentListOf(),
    val nowPage: Int = 0
)

data class NotificationEmojiState(
    val emoji: String,
    val count: Int,
    val isMe: Boolean
)

internal fun NotificationModel.getEmojiList(
    userId: Int
): ImmutableList<NotificationEmojiState> {
    val emojisState = HashMap<String, NotificationEmojiState>()

    this.emoji.fastForEach {
        if (it.emoji in emojisState.keys) {
            val item = emojisState[it.emoji]!!
            emojisState[it.emoji] = item.copy(
                emoji = it.emoji,
                count = item.count + 1,
                isMe = item.isMe || it.userId == userId
            )
            return@fastForEach
        }
        emojisState[it.emoji] = NotificationEmojiState(
            emoji = it.emoji,
            count = 1,
            isMe = it.userId == userId
        )
    }
    return emojisState.values.toImmutableList()
}