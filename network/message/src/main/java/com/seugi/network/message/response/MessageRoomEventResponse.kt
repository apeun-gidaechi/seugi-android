package com.seugi.network.message.response

import com.seugi.network.message.response.message.MessageEmojiResponse
import java.time.LocalDateTime

sealed class MessageRoomEventResponse(
    @Transient open val type: String,
    @Transient open val userId: Int,
) {

    data class Raw(
        override val type: String,
        override val userId: Int
    ) : MessageRoomEventResponse(type, userId)

    sealed class MessageParent(
        @Transient open val timestamp: LocalDateTime,
        @Transient override val type: String,
        @Transient override val userId: Int,
    ): MessageRoomEventResponse(type, userId) {
        data class Message(
            val id: String,
            val chatRoomId: String,
            override val type: String,
            override val userId: Int,
            val message: String,
            val messageStatus: String,
            val uuid: String?,
            val emoticon: String?,
            val eventList: List<Int>?,
            val emojiList: List<MessageEmojiResponse>,
            val mention: List<Int>,
            val mentionAll: Boolean,
            override val timestamp: LocalDateTime,
            val read: List<Int>,
        ) : MessageParent(timestamp, type, userId)
    }

    data class Sub(
        override val type: String,
        override val userId: Int,
    ): MessageRoomEventResponse(type, userId)

    data class DeleteMessage(
        override val type: String,
        override val userId: Int,
        val messageId: String,
    ): MessageRoomEventResponse(type, userId)

    data class AddEmoji(
        override val type: String,
        override val userId: Int,
        val messageId: String,
        val emojiId: Int
    ): MessageRoomEventResponse(type, userId)

    data class RemoveEmoji(
        override val type: String,
        override val userId: Int,
        val messageId: String,
        val emojiId: Int
    ): MessageRoomEventResponse(type, userId)

    data class TransperAdmin(
        override val type: String,
        override val userId: Int,
        val roomId: String,
        val eventList: List<Int>
    ): MessageRoomEventResponse(type, userId)
}