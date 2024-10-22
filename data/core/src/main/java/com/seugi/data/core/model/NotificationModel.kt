package com.seugi.data.core.model

import java.time.LocalDateTime
import kotlinx.collections.immutable.ImmutableList

data class NotificationModel(
    val id: Long,
    val workspaceId: String,
    val userId: Long,
    val userName: String,
    val title: String,
    val content: String,
    val emoji: ImmutableList<NotificationEmojiModel>,
    val creationDate: LocalDateTime,
    val lastModifiedDate: LocalDateTime,
) {
    override fun equals(other: Any?): Boolean {
        if (
            other is NotificationModel && other.id == this.id &&
            other.workspaceId == this.workspaceId &&
            other.emoji == this.emoji
        ) {
            return true
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + workspaceId.hashCode()
        result = 31 * result + userName.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + emoji.hashCode()
        result = 31 * result + creationDate.hashCode()
        result = 31 * result + lastModifiedDate.hashCode()
        return result
    }
}
