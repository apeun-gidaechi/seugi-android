package com.seugi.data.message.mapper

import android.util.Log
import com.seugi.data.core.mapper.toModels
import com.seugi.data.core.model.MealModel
import com.seugi.data.core.model.NotificationModel
import com.seugi.data.core.model.TimetableModel
import com.seugi.data.message.model.MessageBotRawKeyword
import com.seugi.data.message.model.MessageBotRawKeywordInData
import com.seugi.data.message.model.MessageRoomEvent
import com.seugi.network.core.utiles.toResponse
import com.seugi.network.meal.response.MealResponse
import com.seugi.network.message.response.MessageRoomEventResponse
import com.seugi.network.notification.response.NotificationResponse
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

internal fun MessageRoomEventResponse.toEventModel(userId: Long): MessageRoomEvent = when (this) {
    is MessageRoomEventResponse.MessageParent.Message -> toModel(userId)
    is MessageRoomEventResponse.DeleteMessage -> toModel()
    is MessageRoomEventResponse.AddEmoji -> toModel()
    is MessageRoomEventResponse.Raw -> throw IllegalArgumentException("Not Invalid MessageRoomEventResponse.Raw Type")
    is MessageRoomEventResponse.RemoveEmoji -> toModel()
    is MessageRoomEventResponse.Sub -> toModel()
    is MessageRoomEventResponse.TransperAdmin -> toModel()
    is MessageRoomEventResponse.UnSub -> toModel()
}

internal fun MessageRoomEventResponse.MessageParent.Message.toModel(userId: Int): MessageRoomEvent.MessageParent {
    Log.d("TAG", "toModel: $type")
    return when (type) {
        "BOT" -> {
            Log.d("TAG", "toModel: HI ${this.message}")
            val botKeyword = this.message.toResponse(MessageBotRawKeyword::class.java)
            Log.d("TAG", "toModel: HI ${botKeyword.keyword}")
            when (botKeyword.keyword) {
                "급식" -> {
                    val botData = message.toResponse<MessageBotRawKeywordInData<List<MealResponse>>>()
                    MessageRoomEvent.MessageParent.BOT.Meal(
                        id = id,
                        chatRoomId = chatRoomId,
                        type = type.toMessageType(),
                        userId = this.userId,
                        isFirst = true,
                        isLast = true,
                        message = botData.data.toModels().toImmutableList(),
                        messageStatus = messageStatus,
                        emoticon = emoticon,
                        eventList = eventList?.toImmutableList() ?: persistentListOf(),
                        emojiList = emojiList.map { it.toModel() }.toImmutableList(),
                        mention = mention.toImmutableList(),
                        mentionAll = mentionAll,
                        timestamp = timestamp,
                    )
                }
                "시간표" -> {
                    val botData = message.toResponse<MessageBotRawKeywordInData<List<TimetableModel>>>()
                    MessageRoomEvent.MessageParent.BOT.Timetable(
                        id = id,
                        chatRoomId = chatRoomId,
                        type = type.toMessageType(),
                        userId = this.userId,
                        isFirst = true,
                        isLast = true,
                        message = botData.data.toImmutableList(),
                        messageStatus = messageStatus,
                        emoticon = emoticon,
                        eventList = eventList?.toImmutableList() ?: persistentListOf(),
                        emojiList = emojiList.map { it.toModel() }.toImmutableList(),
                        mention = mention.toImmutableList(),
                        mentionAll = mentionAll,
                        timestamp = timestamp,
                    )
                }
                "공지" -> {
                    val botData = message.toResponse<MessageBotRawKeywordInData<List<NotificationResponse>>>()
                    MessageRoomEvent.MessageParent.BOT.Notification(
                        id = id,
                        chatRoomId = chatRoomId,
                        type = type.toMessageType(),
                        userId = this.userId,
                        isFirst = true,
                        isLast = true,
                        message = botData.data.toModels().toImmutableList(),
                        messageStatus = messageStatus,
                        emoticon = emoticon,
                        eventList = eventList?.toImmutableList() ?: persistentListOf(),
                        emojiList = emojiList.map { it.toModel() }.toImmutableList(),
                        mention = mention.toImmutableList(),
                        mentionAll = mentionAll,
                        timestamp = timestamp,
                    )
                }
                "사람 뽑기" -> {
                    val botData = message.toResponse<MessageBotRawKeywordInData<String>>()
                    MessageRoomEvent.MessageParent.BOT.DrawLots(
                        id = id,
                        chatRoomId = chatRoomId,
                        type = type.toMessageType(),
                        userId = this.userId,
                        isFirst = true,
                        isLast = true,
                        message = botData.data,
                        visibleMessage = botData.data,
                        messageStatus = messageStatus,
                        emoticon = emoticon,
                        eventList = eventList?.toImmutableList() ?: persistentListOf(),
                        emojiList = emojiList.map { it.toModel() }.toImmutableList(),
                        mention = mention.toImmutableList(),
                        mentionAll = mentionAll,
                        timestamp = timestamp,
                    )
                }
                else -> throw IllegalArgumentException("알 수 없는 응답")
            }
        }
        "MESSAGE" -> {
            if (userId == this.userId) {
                MessageRoomEvent.MessageParent.Me(
                    id = id,
                    chatRoomId = chatRoomId,
                    type = type.toMessageType(),
                    userId = this.userId,
                    isLast = false,
                    message = message,
                    messageStatus = messageStatus,
                    uuid = uuid,
                    emoticon = emoticon,
                    eventList = eventList?.toImmutableList() ?: persistentListOf(),
                    emojiList = emojiList.map { it.toModel() }.toImmutableList(),
                    mention = mention.toImmutableList(),
                    mentionAll = mentionAll,
                    timestamp = timestamp,
                )
            } else {
                MessageRoomEvent.MessageParent.Other(
                    id = id,
                    chatRoomId = chatRoomId,
                    type = type.toMessageType(),
                    userId = this.userId,
                    isFirst = false,
                    isLast = false,
                    message = message,
                    messageStatus = messageStatus,
                    uuid = uuid,
                    emoticon = emoticon,
                    eventList = eventList?.toImmutableList() ?: persistentListOf(),
                    emojiList = emojiList.map { it.toModel() }.toImmutableList(),
                    mention = mention.toImmutableList(),
                    mentionAll = mentionAll,
                    timestamp = timestamp,
                )
            }
        }
        "IMG" -> {
            val text = message.split("::")
            MessageRoomEvent.MessageParent.Img(
                url = text[0],
                fileName = text[1],
                timestamp = timestamp,
                type = type.toMessageType(),
                userId = this.userId,
                uuid = uuid,
            )
        }
        "FILE" -> {
            val text = message.split("::")
            MessageRoomEvent.MessageParent.File(
                url = text[0],
                fileName = text[1],
                fileSize = text[2].toLong(),
                timestamp = timestamp,
                type = type.toMessageType(),
                userId = this.userId,
                uuid = uuid,
            )
        }

        "ENTER" -> {
            MessageRoomEvent.MessageParent.Enter(
                type = type.toMessageType(),
                userId = this.userId,
                timestamp = timestamp,
                roomId = chatRoomId,
                eventList = eventList?.toImmutableList() ?: persistentListOf(),
            )
        }
        "LEFT" -> {
            MessageRoomEvent.MessageParent.Left(
                type = type.toMessageType(),
                userId = this.userId,
                timestamp = timestamp,
                roomId = chatRoomId,
                eventList = eventList?.toImmutableList() ?: persistentListOf(),
            )
        }

        else -> throw IllegalArgumentException("Not Invalid MessageRoomEventResponse.Raw Type")
    }
}

// event mapper
internal fun MessageRoomEventResponse.DeleteMessage.toModel() = MessageRoomEvent.DeleteMessage(
    type = type.toMessageType(),
    userId = userId,
    messageId = messageId,
)

internal fun MessageRoomEventResponse.AddEmoji.toModel() = MessageRoomEvent.AddEmoji(
    type = type.toMessageType(),
    userId = userId,
    messageId = messageId,
    emojiId = emojiId,
)

internal fun MessageRoomEventResponse.RemoveEmoji.toModel() = MessageRoomEvent.RemoveEmoji(
    type = type.toMessageType(),
    userId = userId,
    messageId = messageId,
    emojiId = emojiId,
)

internal fun MessageRoomEventResponse.Sub.toModel() = MessageRoomEvent.Sub(
    type = type.toMessageType(),
    userId = userId,
)

internal fun MessageRoomEventResponse.UnSub.toModel() = MessageRoomEvent.UnSub(
    type = type.toMessageType(),
    userId = userId,
)

internal fun MessageRoomEventResponse.TransperAdmin.toModel() = MessageRoomEvent.TransperAdmin(
    type = type.toMessageType(),
    userId = userId,
    roomId = roomId,
    eventList = eventList.toImmutableList(),
)
