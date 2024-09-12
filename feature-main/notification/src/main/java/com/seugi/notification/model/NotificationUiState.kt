package com.seugi.notification.model

import androidx.compose.ui.util.fastForEach
import com.seugi.data.notification.model.NotificationModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class NotificationUiState(
    val isRefresh: Boolean = false,
    val notices: ImmutableList<NotificationModel> = persistentListOf(),
    val nowPage: Int = 0,
) {
    override fun equals(other: Any?): Boolean {
        return other is NotificationUiState &&
            this.isRefresh == other.isRefresh &&
            other.notices == this.notices &&
            this.nowPage == other.nowPage
    }

    override fun hashCode(): Int {
        var result = isRefresh.hashCode()
        result = 31 * result + notices.hashCode()
        result = 31 * result + nowPage
        return result
    }
}

data class NotificationEmojiState(
    val emoji: String,
    val count: Int,
    val isMe: Boolean,
) {
    override fun equals(other: Any?): Boolean {
        return other is NotificationEmojiState &&
            this.emoji == other.emoji &&
            this.count == other.count &&
            this.isMe == other.isMe
    }

    override fun hashCode(): Int {
        var result = emoji.hashCode()
        result = 31 * result + count
        result = 31 * result + isMe.hashCode()
        return result
    }
}