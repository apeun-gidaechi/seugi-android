package com.seugi.data.message.model

import com.seugi.data.core.model.MealModel
import com.seugi.data.core.model.NotificationModel
import com.seugi.data.core.model.TimetableModel
import com.seugi.data.core.model.UserInfoModel
import com.seugi.data.message.model.MessageRoomEvent.MessageParent
import java.time.LocalDateTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

sealed class MessageRoomEvent(
    @Transient open val type: MessageType,
    @Transient open val userId: Long,
) {
    sealed class MessageParent(
        @Transient open val timestamp: LocalDateTime,
        @Transient override val type: MessageType,
        @Transient override val userId: Long,
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
            val eventList: ImmutableList<Long>?,
            val emojiList: ImmutableList<MessageEmojiModel>,
            val mention: ImmutableList<Long>,
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
            val eventList: ImmutableList<Long>?,
            val emojiList: ImmutableList<MessageEmojiModel>,
            val mention: ImmutableList<Long>,
            val mentionAll: Boolean,
            override val timestamp: LocalDateTime,
        ) : MessageParent(timestamp, type, userId)

        sealed class BOT(
            @Transient open val id: String,
            @Transient open val chatRoomId: String,
            @Transient open val isFirst: Boolean,
            @Transient open val isLast: Boolean,
            @Transient override val type: MessageType,
            @Transient override val userId: Long,
            @Transient open val messageStatus: String,
            @Transient open val emoticon: String?,
            @Transient open val eventList: ImmutableList<Long>?,
            @Transient open val emojiList: ImmutableList<MessageEmojiModel>,
            @Transient open val mention: ImmutableList<Long>,
            @Transient open val mentionAll: Boolean,
            @Transient override val timestamp: LocalDateTime,
        ) : MessageParent(timestamp, type, userId) {
            data class Meal(
                override val type: MessageType,
                val message: ImmutableList<MealModel>,
                val visibleMessage: String,
                override val messageStatus: String,
                override val emoticon: String?,
                override val eventList: ImmutableList<Long>?,
                override val id: String,
                override val emojiList: ImmutableList<MessageEmojiModel>,
                override val chatRoomId: String,
                override val timestamp: LocalDateTime,
                override val userId: Long,
                override val mention: ImmutableList<Long>,
                override val mentionAll: Boolean,
                override val isFirst: Boolean,
                override val isLast: Boolean,
            ) : BOT(id, chatRoomId, isFirst, isLast, type, userId, messageStatus, emoticon, eventList, emojiList, mention, mentionAll, timestamp)

            data class Timetable(
                override val type: MessageType,
                val message: ImmutableList<TimetableModel>,
                val visibleMessage: String,
                override val messageStatus: String,
                override val emoticon: String?,
                override val eventList: ImmutableList<Long>?,
                override val id: String,
                override val emojiList: ImmutableList<MessageEmojiModel>,
                override val chatRoomId: String,
                override val timestamp: LocalDateTime,
                override val userId: Long,
                override val mention: ImmutableList<Long>,
                override val mentionAll: Boolean,
                override val isFirst: Boolean,
                override val isLast: Boolean,
            ) : BOT(id, chatRoomId, isFirst, isLast, type, userId, messageStatus, emoticon, eventList, emojiList, mention, mentionAll, timestamp)

            data class Notification(
                override val type: MessageType,
                val message: ImmutableList<NotificationModel>,
                val visibleMessage: String,
                override val messageStatus: String,
                override val emoticon: String?,
                override val eventList: ImmutableList<Long>?,
                override val id: String,
                override val emojiList: ImmutableList<MessageEmojiModel>,
                override val chatRoomId: String,
                override val timestamp: LocalDateTime,
                override val userId: Long,
                override val mention: ImmutableList<Long>,
                override val mentionAll: Boolean,
                override val isFirst: Boolean,
                override val isLast: Boolean,
            ) : BOT(id, chatRoomId, isFirst, isLast, type, userId, messageStatus, emoticon, eventList, emojiList, mention, mentionAll, timestamp)

            data class DrawLots(
                override val type: MessageType,
                val message: String,
                val visibleMessage: String,
                override val messageStatus: String,
                override val emoticon: String?,
                override val eventList: ImmutableList<Long>?,
                override val id: String,
                override val emojiList: ImmutableList<MessageEmojiModel>,
                override val chatRoomId: String,
                override val timestamp: LocalDateTime,
                override val userId: Long,
                override val mention: ImmutableList<Long>,
                override val mentionAll: Boolean,
                override val isFirst: Boolean,
                override val isLast: Boolean,
            ) : BOT(id, chatRoomId, isFirst, isLast, type, userId, messageStatus, emoticon, eventList, emojiList, mention, mentionAll, timestamp)

            data class TeamBuild(
                override val type: MessageType,
                val message: String,
                val visibleMessage: String,
                override val messageStatus: String,
                override val emoticon: String?,
                override val eventList: ImmutableList<Long>?,
                override val id: String,
                override val emojiList: ImmutableList<MessageEmojiModel>,
                override val chatRoomId: String,
                override val timestamp: LocalDateTime,
                override val userId: Long,
                override val mention: ImmutableList<Long>,
                override val mentionAll: Boolean,
                override val isFirst: Boolean,
                override val isLast: Boolean,
            ) : BOT(id, chatRoomId, isFirst, isLast, type, userId, messageStatus, emoticon, eventList, emojiList, mention, mentionAll, timestamp)

            data class Etc(
                override val type: MessageType,
                val message: String,
                override val messageStatus: String,
                override val emoticon: String?,
                override val eventList: ImmutableList<Long>?,
                override val id: String,
                override val emojiList: ImmutableList<MessageEmojiModel>,
                override val chatRoomId: String,
                override val timestamp: LocalDateTime,
                override val userId: Long,
                override val mention: ImmutableList<Long>,
                override val mentionAll: Boolean,
                override val isFirst: Boolean,
                override val isLast: Boolean,
            ) : BOT(id, chatRoomId, isFirst, isLast, type, userId, messageStatus, emoticon, eventList, emojiList, mention, mentionAll, timestamp)

            data class NotSupport(
                override val type: MessageType,
                val message: String,
                override val messageStatus: String,
                override val emoticon: String?,
                override val eventList: ImmutableList<Long>?,
                override val id: String,
                override val emojiList: ImmutableList<MessageEmojiModel>,
                override val chatRoomId: String,
                override val timestamp: LocalDateTime,
                override val userId: Long,
                override val mention: ImmutableList<Long>,
                override val mentionAll: Boolean,
                override val isFirst: Boolean,
                override val isLast: Boolean,
            ) : BOT(id, chatRoomId, isFirst, isLast, type, userId, messageStatus, emoticon, eventList, emojiList, mention, mentionAll, timestamp)
        }

        data class File(
            val id: String,
            val chatRoomId: String,
            val url: String,
            val fileName: String,
            val fileSize: Long,
            val uuid: String?,
            val emojiList: ImmutableList<MessageEmojiModel>,
            override val timestamp: LocalDateTime,
            override val type: MessageType,
            override val userId: Long,
            val isFirst: Boolean,
            val isLast: Boolean,
        ) : MessageParent(timestamp, type, userId)

        data class Img(
            val id: String,
            val chatRoomId: String,
            val url: String,
            val fileName: String,
            val uuid: String?,
            val emojiList: ImmutableList<MessageEmojiModel>,
            override val timestamp: LocalDateTime,
            override val type: MessageType,
            override val userId: Long,
            val isFirst: Boolean,
            val isLast: Boolean,
        ) : MessageParent(timestamp, type, userId)

        data class Enter(
            override val type: MessageType,
            override val userId: Long,
            override val timestamp: LocalDateTime,
            val roomId: String,
            val eventList: ImmutableList<Long>,
        ) : MessageParent(timestamp, type, userId)

        data class Left(
            override val type: MessageType,
            override val userId: Long,
            override val timestamp: LocalDateTime,
            val roomId: String,
            val eventList: ImmutableList<Long>,
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
        val eventList: ImmutableList<Long>,
    ) : MessageRoomEvent(type, userId)
}

fun MessageRoomEvent.MessageParent.BOT.copy(
    id: String = this.id,
    chatRoomId: String = this.chatRoomId,
    isFirst: Boolean = this.isFirst,
    isLast: Boolean = this.isLast,
    type: MessageType = this.type,
    userId: Long = this.userId,
    messageStatus: String = this.messageStatus,
    emoticon: String? = this.emoticon,
    eventList: ImmutableList<Long>? = this.eventList,
    emojiList: ImmutableList<MessageEmojiModel> = this.emojiList,
    mention: ImmutableList<Long> = this.mention,
    mentionAll: Boolean = this.mentionAll,
    timestamp: LocalDateTime = this.timestamp,
): MessageRoomEvent.MessageParent.BOT = when (this) {
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

fun MessageRoomEvent.copy(type: MessageType = this.type, userId: Long = this.userId) {
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

fun MessageRoomEvent.MessageParent.BOT.DrawLots.getVisibleMessage(members: List<UserInfoModel>?): String {
    val regex = "::(\\d+)::".toRegex()
    val results = regex.findAll(this.message)

    val numbers = results.mapNotNull { result ->
        result.groupValues[1].toLongOrNull()
    }.toList()

    val visibleMessage = "사람을 ${numbers.size}명 뽑았어요\n" +
        numbers.mapNotNull { number ->
            members?.map { it.userInfo }?.firstOrNull { it.id == number.toLong() }?.name
        }.joinToString(" ")

    return visibleMessage
}

fun MessageRoomEvent.MessageParent.BOT.TeamBuild.getVisibleMessage(members: List<UserInfoModel>?): String {
    var visibleMessage = this.message

    members?.forEach {
        visibleMessage = visibleMessage.replace("::${it.userInfo.id}::", it.userInfo.name)
    }
    return visibleMessage
}

fun MessageRoomEvent.MessageParent.addEmoji(userId: Long, emojiId: Int): MessageRoomEvent.MessageParent = when (this) {
    is MessageRoomEvent.MessageParent.BOT.DrawLots -> this.copy(
        emojiList = this.emojiList.addEmoji(userId, emojiId = emojiId).toImmutableList(),
    )

    is MessageRoomEvent.MessageParent.BOT.Etc -> this.copy(
        emojiList = this.emojiList.addEmoji(userId, emojiId = emojiId).toImmutableList(),
    )

    is MessageRoomEvent.MessageParent.BOT.Meal -> this.copy(
        emojiList = this.emojiList.addEmoji(userId, emojiId = emojiId).toImmutableList(),
    )

    is MessageRoomEvent.MessageParent.BOT.NotSupport -> this.copy(
        emojiList = this.emojiList.addEmoji(userId, emojiId = emojiId).toImmutableList(),
    )

    is MessageRoomEvent.MessageParent.BOT.Notification -> this.copy(
        emojiList = this.emojiList.addEmoji(userId, emojiId = emojiId).toImmutableList(),
    )

    is MessageRoomEvent.MessageParent.BOT.TeamBuild -> this.copy(
        emojiList = this.emojiList.addEmoji(userId, emojiId = emojiId).toImmutableList(),
    )

    is MessageRoomEvent.MessageParent.BOT.Timetable -> this.copy(
        emojiList = this.emojiList.addEmoji(userId, emojiId = emojiId).toImmutableList(),
    )

    is MessageRoomEvent.MessageParent.File -> this.copy(
        emojiList = this.emojiList.addEmoji(userId, emojiId = emojiId).toImmutableList(),
    )

    is MessageRoomEvent.MessageParent.Img -> this.copy(
        emojiList = this.emojiList.addEmoji(userId, emojiId = emojiId).toImmutableList(),
    )

    is MessageRoomEvent.MessageParent.Me -> this.copy(
        emojiList = this.emojiList.addEmoji(userId, emojiId = emojiId).toImmutableList(),
    )

    is MessageRoomEvent.MessageParent.Other -> this.copy(
        emojiList = this.emojiList.addEmoji(userId, emojiId = emojiId).toImmutableList(),
    )

    else -> this
}

fun MessageRoomEvent.MessageParent.minusEmoji(userId: Long, emojiId: Int): MessageRoomEvent.MessageParent = when (this) {
    is MessageRoomEvent.MessageParent.BOT.DrawLots -> this.copy(
        emojiList = this.emojiList.minusEmoji(userId, emojiId = emojiId).toImmutableList(),
    )
    is MessageRoomEvent.MessageParent.BOT.Etc -> this.copy(
        emojiList = this.emojiList.minusEmoji(userId, emojiId = emojiId).toImmutableList(),
    )
    is MessageRoomEvent.MessageParent.BOT.Meal -> this.copy(
        emojiList = this.emojiList.minusEmoji(userId, emojiId = emojiId).toImmutableList(),
    )
    is MessageRoomEvent.MessageParent.BOT.NotSupport -> this.copy(
        emojiList = this.emojiList.minusEmoji(userId, emojiId = emojiId).toImmutableList(),
    )
    is MessageRoomEvent.MessageParent.BOT.Notification -> this.copy(
        emojiList = this.emojiList.minusEmoji(userId, emojiId = emojiId).toImmutableList(),
    )
    is MessageRoomEvent.MessageParent.BOT.TeamBuild -> this.copy(
        emojiList = this.emojiList.minusEmoji(userId, emojiId = emojiId).toImmutableList(),
    )
    is MessageRoomEvent.MessageParent.BOT.Timetable -> this.copy(
        emojiList = this.emojiList.minusEmoji(userId, emojiId = emojiId).toImmutableList(),
    )
    is MessageRoomEvent.MessageParent.File -> this.copy(
        emojiList = this.emojiList.minusEmoji(userId, emojiId = emojiId).toImmutableList(),
    )
    is MessageRoomEvent.MessageParent.Img -> this.copy(
        emojiList = this.emojiList.minusEmoji(userId, emojiId = emojiId).toImmutableList(),
    )
    is MessageRoomEvent.MessageParent.Me -> this.copy(
        emojiList = this.emojiList.minusEmoji(userId, emojiId = emojiId).toImmutableList(),
    )
    is MessageRoomEvent.MessageParent.Other -> this.copy(
        emojiList = this.emojiList.minusEmoji(userId, emojiId = emojiId).toImmutableList(),
    )
    else -> this
}

fun MessageRoomEvent.MessageParent.equalsMessageId(messageId: String): Boolean = when (this) {
    is MessageParent.BOT.DrawLots -> this.id == messageId
    is MessageParent.BOT.Etc -> this.id == messageId
    is MessageParent.BOT.Meal -> this.id == messageId
    is MessageParent.BOT.NotSupport -> this.id == messageId
    is MessageParent.BOT.Notification -> this.id == messageId
    is MessageParent.BOT.TeamBuild -> this.id == messageId
    is MessageParent.BOT.Timetable -> this.id == messageId
    is MessageParent.File -> this.id == messageId
    is MessageParent.Img -> this.id == messageId
    is MessageParent.Me -> this.id == messageId
    is MessageParent.Other -> this.id == messageId
    else -> false
}
