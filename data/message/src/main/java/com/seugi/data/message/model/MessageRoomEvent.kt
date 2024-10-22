package com.seugi.data.message.model

import android.util.Log
import com.seugi.data.core.model.MealModel
import com.seugi.data.core.model.NotificationModel
import com.seugi.data.core.model.TimetableModel
import com.seugi.data.core.model.UserInfoModel
import java.time.LocalDateTime
import kotlinx.collections.immutable.ImmutableList

sealed class MessageRoomEvent(
    @Transient open val type: MessageType,
    @Transient open val userId: Int,
) {
    sealed class MessageParent(
        @Transient open val timestamp: LocalDateTime,
        @Transient override val type: MessageType,
        @Transient override val userId: Int,
    ) : MessageRoomEvent(type, userId) {
        data class Me(
            val id: String,
            val chatRoomId: String,
            val isLast: Boolean,
            override val type: MessageType,
            override val userId: Long,
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
            override val userId: Long,
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

        sealed class BOT(
            @Transient open val id: String,
            @Transient open val chatRoomId: String,
            @Transient open val isFirst: Boolean,
            @Transient open val isLast: Boolean,
            @Transient override val type: MessageType,
            @Transient override val userId: Int,
            @Transient open val messageStatus: String,
            @Transient open val emoticon: String?,
            @Transient open val eventList: ImmutableList<Int>?,
            @Transient open val emojiList: ImmutableList<MessageEmojiModel>,
            @Transient open val mention: ImmutableList<Int>,
            @Transient open val mentionAll: Boolean,
            @Transient override val timestamp: LocalDateTime,
        ) : MessageParent(timestamp, type, userId) {
            data class Meal(
                override val type: MessageType,
                val message: ImmutableList<MealModel>,
                override val messageStatus: String,
                override val emoticon: String?,
                override val eventList: ImmutableList<Int>?,
                override val id: String,
                override val emojiList: ImmutableList<MessageEmojiModel>,
                override val chatRoomId: String,
                override val timestamp: LocalDateTime,
                override val userId: Int,
                override val mention: ImmutableList<Int>,
                override val mentionAll: Boolean,
                override val isFirst: Boolean,
                override val isLast: Boolean,
            ): BOT(id, chatRoomId, isFirst, isLast, type, userId, messageStatus, emoticon, eventList, emojiList, mention, mentionAll, timestamp)

            data class Timetable(
                override val type: MessageType,
                val message: ImmutableList<TimetableModel>,
                override val messageStatus: String,
                override val emoticon: String?,
                override val eventList: ImmutableList<Int>?,
                override val id: String,
                override val emojiList: ImmutableList<MessageEmojiModel>,
                override val chatRoomId: String,
                override val timestamp: LocalDateTime,
                override val userId: Int,
                override val mention: ImmutableList<Int>,
                override val mentionAll: Boolean,
                override val isFirst: Boolean,
                override val isLast: Boolean,
            ): BOT(id, chatRoomId, isFirst, isLast, type, userId, messageStatus, emoticon, eventList, emojiList, mention, mentionAll, timestamp)

            data class Notification(
                override val type: MessageType,
                val message: ImmutableList<NotificationModel>,
                override val messageStatus: String,
                override val emoticon: String?,
                override val eventList: ImmutableList<Int>?,
                override val id: String,
                override val emojiList: ImmutableList<MessageEmojiModel>,
                override val chatRoomId: String,
                override val timestamp: LocalDateTime,
                override val userId: Int,
                override val mention: ImmutableList<Int>,
                override val mentionAll: Boolean,
                override val isFirst: Boolean,
                override val isLast: Boolean,
            ): BOT(id, chatRoomId, isFirst, isLast, type, userId, messageStatus, emoticon, eventList, emojiList, mention, mentionAll, timestamp)

            data class DrawLots(
                override val type: MessageType,
                val message: String,
                val visibleMessage: String,
                override val messageStatus: String,
                override val emoticon: String?,
                override val eventList: ImmutableList<Int>?,
                override val id: String,
                override val emojiList: ImmutableList<MessageEmojiModel>,
                override val chatRoomId: String,
                override val timestamp: LocalDateTime,
                override val userId: Int,
                override val mention: ImmutableList<Int>,
                override val mentionAll: Boolean,
                override val isFirst: Boolean,
                override val isLast: Boolean,
            ): BOT(id, chatRoomId, isFirst, isLast, type, userId, messageStatus, emoticon, eventList, emojiList, mention, mentionAll, timestamp)

            data class TeamBuild(
                override val type: MessageType,
                val message: String,
                val visibleMessage: String,
                override val messageStatus: String,
                override val emoticon: String?,
                override val eventList: ImmutableList<Int>?,
                override val id: String,
                override val emojiList: ImmutableList<MessageEmojiModel>,
                override val chatRoomId: String,
                override val timestamp: LocalDateTime,
                override val userId: Int,
                override val mention: ImmutableList<Int>,
                override val mentionAll: Boolean,
                override val isFirst: Boolean,
                override val isLast: Boolean,
            ): BOT(id, chatRoomId, isFirst, isLast, type, userId, messageStatus, emoticon, eventList, emojiList, mention, mentionAll, timestamp)

            data class Etc(
                override val type: MessageType,
                val message: String,
                override val messageStatus: String,
                override val emoticon: String?,
                override val eventList: ImmutableList<Int>?,
                override val id: String,
                override val emojiList: ImmutableList<MessageEmojiModel>,
                override val chatRoomId: String,
                override val timestamp: LocalDateTime,
                override val userId: Int,
                override val mention: ImmutableList<Int>,
                override val mentionAll: Boolean,
                override val isFirst: Boolean,
                override val isLast: Boolean,
            ): BOT(id, chatRoomId, isFirst, isLast, type, userId, messageStatus, emoticon, eventList, emojiList, mention, mentionAll, timestamp)

            data class NotSupport(
                override val type: MessageType,
                val message: String,
                override val messageStatus: String,
                override val emoticon: String?,
                override val eventList: ImmutableList<Int>?,
                override val id: String,
                override val emojiList: ImmutableList<MessageEmojiModel>,
                override val chatRoomId: String,
                override val timestamp: LocalDateTime,
                override val userId: Int,
                override val mention: ImmutableList<Int>,
                override val mentionAll: Boolean,
                override val isFirst: Boolean,
                override val isLast: Boolean,
            ): BOT(id, chatRoomId, isFirst, isLast, type, userId, messageStatus, emoticon, eventList, emojiList, mention, mentionAll, timestamp)
        }

        data class File(
            val url: String,
            val fileName: String,
            val fileSize: Long,
            val uuid: String?,
            override val timestamp: LocalDateTime,
            override val type: MessageType,
            override val userId: Long,
        ) : MessageParent(timestamp, type, userId)

        data class Img(
            val url: String,
            val fileName: String,
            val uuid: String?,
            override val timestamp: LocalDateTime,
            override val type: MessageType,
            override val userId: Long,
        ) : MessageParent(timestamp, type, userId)

        data class Enter(
            override val type: MessageType,
            override val userId: Long,
            override val timestamp: LocalDateTime,
            val roomId: String,
            val eventList: ImmutableList<Int>,
        ) : MessageParent(timestamp, type, userId)

        data class Left(
            override val type: MessageType,
            override val userId: Long,
            override val timestamp: LocalDateTime,
            val roomId: String,
            val eventList: ImmutableList<Int>,
        ) : MessageParent(timestamp, type, userId)

        data class Date(
            override val type: MessageType,
            override val userId: Long,
            override val timestamp: LocalDateTime,
            val text: String,
        ) : MessageParent(timestamp, type, userId)

        data class Etc(
            override val type: MessageType,
            override val userId: Long,
            override val timestamp: LocalDateTime,
            val text: String,
        ) : MessageParent(timestamp, type, userId)
    }

    data class Sub(
        override val type: MessageType,
        override val userId: Long,
    ) : MessageRoomEvent(type, userId)
    data class UnSub(
        override val type: MessageType,
        override val userId: Long,
    ) : MessageRoomEvent(type, userId)

    data class DeleteMessage(
        override val type: MessageType,
        override val userId: Long,
        val messageId: String,
    ) : MessageRoomEvent(type, userId)

    data class AddEmoji(
        override val type: MessageType,
        override val userId: Long,
        val messageId: String,
        val emojiId: Int,
    ) : MessageRoomEvent(type, userId)

    data class RemoveEmoji(
        override val type: MessageType,
        override val userId: Long,
        val messageId: String,
        val emojiId: Int,
    ) : MessageRoomEvent(type, userId)

    data class TransperAdmin(
        override val type: MessageType,
        override val userId: Long,
        val roomId: String,
        val eventList: ImmutableList<Int>,
    ) : MessageRoomEvent(type, userId)
}

fun MessageRoomEvent.MessageParent.BOT.copy(
    id: String = this.id,
    chatRoomId: String = this.chatRoomId,
    isFirst: Boolean = this.isFirst,
    isLast: Boolean = this.isLast,
    type: MessageType = this.type,
    userId: Int = this.userId,
    messageStatus: String = this.messageStatus,
    emoticon: String? = this.emoticon,
    eventList: ImmutableList<Int>? = this.eventList,
    emojiList: ImmutableList<MessageEmojiModel> = this.emojiList,
    mention: ImmutableList<Int> = this.mention,
    mentionAll: Boolean = this.mentionAll,
    timestamp: LocalDateTime = this.timestamp,
): MessageRoomEvent.MessageParent.BOT =
    when (this) {
        is MessageRoomEvent.MessageParent.BOT.Meal -> {
            this.copy(
                type = type,
                message = this.message,
                messageStatus = messageStatus,
                emoticon = emoticon,
                eventList = eventList,
                id = id,
                emojiList = emojiList,
                chatRoomId = chatRoomId,
                timestamp = timestamp,
                userId = userId,
                mention = mention,
                mentionAll = mentionAll,
                isFirst = isFirst,
                isLast = isLast,
            )
        }

        is MessageRoomEvent.MessageParent.BOT.Timetable -> {
            this.copy(
                type = type,
                message = this.message,
                messageStatus = messageStatus,
                emoticon = emoticon,
                eventList = eventList,
                id = id,
                emojiList = emojiList,
                chatRoomId = chatRoomId,
                timestamp = timestamp,
                userId = userId,
                mention = mention,
                mentionAll = mentionAll,
                isFirst = isFirst,
                isLast = isLast,
            )
        }
        is MessageRoomEvent.MessageParent.BOT.Notification -> {
            this.copy(
                type = type,
                message = this.message,
                messageStatus = messageStatus,
                emoticon = emoticon,
                eventList = eventList,
                id = id,
                emojiList = emojiList,
                chatRoomId = chatRoomId,
                timestamp = timestamp,
                userId = userId,
                mention = mention,
                mentionAll = mentionAll,
                isFirst = isFirst,
                isLast = isLast,
            )
        }

        is MessageRoomEvent.MessageParent.BOT.DrawLots -> {
            this.copy(
                type = type,
                message = this.message,
                messageStatus = messageStatus,
                emoticon = emoticon,
                eventList = eventList,
                id = id,
                emojiList = emojiList,
                chatRoomId = chatRoomId,
                timestamp = timestamp,
                userId = userId,
                mention = mention,
                mentionAll = mentionAll,
                isFirst = isFirst,
                isLast = isLast,
            )
        }

        is MessageRoomEvent.MessageParent.BOT.TeamBuild -> {
            this.copy(
                type = type,
                message = this.message,
                messageStatus = messageStatus,
                emoticon = emoticon,
                eventList = eventList,
                id = id,
                emojiList = emojiList,
                chatRoomId = chatRoomId,
                timestamp = timestamp,
                userId = userId,
                mention = mention,
                mentionAll = mentionAll,
                isFirst = isFirst,
                isLast = isLast,
            )
        }

        is MessageRoomEvent.MessageParent.BOT.Etc -> {
            this.copy(
                type = type,
                message = this.message,
                messageStatus = messageStatus,
                emoticon = emoticon,
                eventList = eventList,
                id = id,
                emojiList = emojiList,
                chatRoomId = chatRoomId,
                timestamp = timestamp,
                userId = userId,
                mention = mention,
                mentionAll = mentionAll,
                isFirst = isFirst,
                isLast = isLast,
            )
        }

        is MessageRoomEvent.MessageParent.BOT.NotSupport -> {
            this.copy(
                type = type,
                message = this.message,
                messageStatus = messageStatus,
                emoticon = emoticon,
                eventList = eventList,
                id = id,
                emojiList = emojiList,
                chatRoomId = chatRoomId,
                timestamp = timestamp,
                userId = userId,
                mention = mention,
                mentionAll = mentionAll,
                isFirst = isFirst,
                isLast = isLast,
            )
        }
    }

fun MessageRoomEvent.copy(
    type: MessageType = this.type,
    userId: Int = this.userId,
) {
    when (this) {
        is MessageRoomEvent.AddEmoji -> copy(
            type = type,
            userId = userId,
        )

        is MessageRoomEvent.DeleteMessage -> this.copy(
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

        is MessageRoomEvent.UnSub -> copy(
            type = type,
            userId = userId,
        )
        is MessageRoomEvent.MessageParent.BOT.Meal -> TODO()
        is MessageRoomEvent.MessageParent.Date -> TODO()
        is MessageRoomEvent.MessageParent.Enter -> TODO()
        is MessageRoomEvent.MessageParent.Etc -> TODO()
        is MessageRoomEvent.MessageParent.File -> TODO()
        is MessageRoomEvent.MessageParent.Img -> TODO()
        is MessageRoomEvent.MessageParent.Left -> TODO()
        is MessageRoomEvent.MessageParent.Me -> TODO()
        is MessageRoomEvent.MessageParent.Other -> TODO()
        is MessageRoomEvent.MessageParent.BOT.Timetable -> TODO()
        is MessageRoomEvent.MessageParent.BOT.Notification -> TODO()
        is MessageRoomEvent.MessageParent.BOT.DrawLots -> TODO()
        is MessageRoomEvent.MessageParent.BOT.TeamBuild -> TODO()
        is MessageRoomEvent.MessageParent.BOT.Etc -> TODO()
        is MessageRoomEvent.MessageParent.BOT.NotSupport -> TODO()
    }

}

fun MessageRoomEvent.MessageParent.BOT.DrawLots.getVisibleMessage(
    members: List<UserInfoModel>?
): String {

    val regex = "::(\\d+)::".toRegex()
    val results = regex.findAll(this.message)

    val numbers = results.mapNotNull { result ->
        result.groupValues[1].toIntOrNull()
    }.toList()

    val visibleMessage = "사람을 ${numbers.size}명 뽑았어요\n" +
            numbers.mapNotNull { number ->
                members?.map { it.userInfo }?.firstOrNull { it.id == number }?.name
            }.joinToString(" ")

    return visibleMessage
}

fun MessageRoomEvent.MessageParent.BOT.TeamBuild.getVisibleMessage(
    members: List<UserInfoModel>?
): String {

    var visibleMessage = this.message

    members?.forEach {
        visibleMessage = visibleMessage.replace("::${it.userInfo.id}::", it.userInfo.name)
    }
    return visibleMessage
}