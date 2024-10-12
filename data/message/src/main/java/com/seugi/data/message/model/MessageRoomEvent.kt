package com.seugi.data.message.model

import java.time.LocalDateTime
import kotlinx.collections.immutable.ImmutableList

sealed class MessageRoomEvent(
    open val type: MessageType,
    open val userId: Int,
) {
    sealed class MessageParent(
        open val timestamp: LocalDateTime,
        override val type: MessageType,
        override val userId: Int,
    ) : MessageRoomEvent(type, userId) {
        data class Me(
            val id: String,
            val chatRoomId: String,
            val isLast: Boolean,
            override val type: MessageType,
            override val userId: Int,
            val message: String,
            val messageStatus: String,
            val uuid: String?,
            val emoticon: String?,
            val eventList: ImmutableList<Int>?,
            val emojiList: ImmutableList<MessageEmojiModel>,
            val mention: ImmutableList<Int>,
            val mentionAll: Boolean,
            override val timestamp: LocalDateTime,
        ) : MessageParent(timestamp, type, userId)

        data class Other(
            val id: String,
            val chatRoomId: String,
            val isFirst: Boolean,
            val isLast: Boolean,
            override val type: MessageType,
            override val userId: Int,
            val message: String,
            val messageStatus: String,
            val uuid: String?,
            val emoticon: String?,
            val eventList: ImmutableList<Int>?,
            val emojiList: ImmutableList<MessageEmojiModel>,
            val mention: ImmutableList<Int>,
            val mentionAll: Boolean,
            override val timestamp: LocalDateTime,
        ) : MessageParent(timestamp, type, userId)

        data class File(
            val url: String,
            val fileName: String,
            val fileSize: Long,
            val uuid: String?,
            override val timestamp: LocalDateTime,
            override val type: MessageType,
            override val userId: Int,
        ) : MessageParent(timestamp, type, userId)

        data class Img(
            val url: String,
            val fileName: String,
            val uuid: String?,
            override val timestamp: LocalDateTime,
            override val type: MessageType,
            override val userId: Int,
        ) : MessageParent(timestamp, type, userId)

        data class Enter(
            override val type: MessageType,
            override val userId: Int,
            override val timestamp: LocalDateTime,
            val roomId: String,
            val eventList: ImmutableList<Int>,
        ) : MessageParent(timestamp, type, userId)

        data class Left(
            override val type: MessageType,
            override val userId: Int,
            override val timestamp: LocalDateTime,
            val roomId: String,
            val eventList: ImmutableList<Int>,
        ) : MessageParent(timestamp, type, userId)

        data class Date(
            override val type: MessageType,
            override val userId: Int,
            override val timestamp: LocalDateTime,
            val text: String,
        ) : MessageParent(timestamp, type, userId)

        data class Etc(
            override val type: MessageType,
            override val userId: Int,
            override val timestamp: LocalDateTime,
            val text: String,
        ) : MessageParent(timestamp, type, userId)
    }

    data class Sub(
        override val type: MessageType,
        override val userId: Int,
    ) : MessageRoomEvent(type, userId)

    data class DeleteMessage(
        override val type: MessageType,
        override val userId: Int,
        val messageId: String,
    ) : MessageRoomEvent(type, userId)

    data class AddEmoji(
        override val type: MessageType,
        override val userId: Int,
        val messageId: String,
        val emojiId: Int,
    ) : MessageRoomEvent(type, userId)

    data class RemoveEmoji(
        override val type: MessageType,
        override val userId: Int,
        val messageId: String,
        val emojiId: Int,
    ) : MessageRoomEvent(type, userId)

    data class TransperAdmin(
        override val type: MessageType,
        override val userId: Int,
        val roomId: String,
        val eventList: ImmutableList<Int>,
    ) : MessageRoomEvent(type, userId)
}

fun MessageRoomEvent.copy(type: MessageType = this.type, userId: Int = this.userId) {
    when (this) {
        is MessageRoomEvent.AddEmoji -> copy(
            type = type,
            userId = userId,
        )
        is MessageRoomEvent.DeleteMessage -> this.copy(
            type = type,
            userId = userId,
        )
        is MessageRoomEvent.MessageParent -> copy(
            type = type,
            userId = userId,
        )
        is MessageRoomEvent.RemoveEmoji -> copy(
            type = type,
            userId = userId,
        )
        is MessageRoomEvent.Sub -> copy(
            type = type,
            userId = userId,
        )
        is MessageRoomEvent.TransperAdmin -> copy(
            type = type,
            userId = userId,
        )
    }
}

fun MessageRoomEvent.MessageParent.copy(timestamp: LocalDateTime = this.timestamp, type: MessageType = this.type, userId: Int = this.userId) = when (this) {
    is MessageRoomEvent.MessageParent.Enter -> {
        this.copy(
            type = type,
            userId = userId,
            timestamp = timestamp,
        )
    }
    is MessageRoomEvent.MessageParent.File -> {
        this.copy(
            type = type,
            userId = userId,
            timestamp = timestamp,
        )
    }
    is MessageRoomEvent.MessageParent.Img -> {
        this.copy(
            type = type,
            userId = userId,
            timestamp = timestamp,
        )
    }
    is MessageRoomEvent.MessageParent.Left -> {
        this.copy(
            type = type,
            userId = userId,
            timestamp = timestamp,
        )
    }
    is MessageRoomEvent.MessageParent.Me -> {
        this.copy(
            type = type,
            userId = userId,
            timestamp = timestamp,
        )
    }
    is MessageRoomEvent.MessageParent.Other -> {
        this.copy(
            type = type,
            userId = userId,
            timestamp = timestamp,
        )
    }

    is MessageRoomEvent.MessageParent.Date -> {
        this.copy(
            type = type,
            userId = userId,
            timestamp = timestamp,
        )
    }
    is MessageRoomEvent.MessageParent.Etc -> {
        this.copy(
            type = type,
            userId = userId,
            timestamp = timestamp,
        )
    }
}
