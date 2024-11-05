package com.seugi.data.message.mapper

import android.util.Log
import com.seugi.data.core.mapper.toModels
import com.seugi.data.core.model.MealType
import com.seugi.data.message.model.MessageBotRawKeyword
import com.seugi.data.message.model.MessageBotRawKeywordInData
import com.seugi.data.message.model.MessageRoomEvent
import com.seugi.network.core.utiles.toResponse
import com.seugi.network.meal.response.MealResponse
import com.seugi.network.message.response.MessageRoomEventResponse
import com.seugi.network.notification.response.NotificationResponse
import com.seugi.network.timetable.response.TimetableResponse
import java.time.LocalDate
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.toKotlinLocalDate

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

internal fun MessageRoomEventResponse.MessageParent.Message.toModel(userId: Long): MessageRoomEvent.MessageParent {
    return when (type) {
        "BOT" -> {
            val botKeyword = this.message.toResponse(MessageBotRawKeyword::class.java)
            when (botKeyword.keyword) {
                "급식" -> {
                    val botData = message.toResponse<MessageBotRawKeywordInData<List<MealResponse>>>()
                    val response = botData.data
                        .toModels()
                        .toImmutableList()

                    val breakfast = response.firstOrNull { it.mealType == MealType.BREAKFAST }
                    val lunch = response.firstOrNull { it.mealType == MealType.LUNCH }
                    val dinner = response.firstOrNull { it.mealType == MealType.DINNER }

                    var visibleMessage = ""

                    if (breakfast != null) {
                        visibleMessage += "- 오늘의 조식\n"
                        breakfast.menu.forEachIndexed { index, s ->
                            visibleMessage += s
                            if (index != breakfast.menu.lastIndex) {
                                visibleMessage += "\n"
                            }
                        }

                        if (lunch != null || dinner != null) {
                            visibleMessage += "\n\n"
                        }
                    }

                    if (lunch != null) {
                        visibleMessage += "- 오늘의 중식\n"
                        lunch.menu.forEachIndexed { index, s ->
                            visibleMessage += s
                            if (index != lunch.menu.lastIndex) {
                                visibleMessage += "\n"
                            }
                        }
                        if (dinner != null) {
                            visibleMessage += "\n\n"
                        }
                    }

                    if (dinner != null) {
                        visibleMessage += "- 오늘의 석식\n"
                        dinner.menu.forEachIndexed { index, s ->
                            visibleMessage += s
                            if (index != dinner.menu.lastIndex) {
                                visibleMessage += "\n"
                            }
                        }
                    }

                    if (visibleMessage == "") {
                        visibleMessage = "오늘의 급식 없습니다."
                    }

                    MessageRoomEvent.MessageParent.BOT.Meal(
                        id = id,
                        chatRoomId = chatRoomId,
                        type = type.toMessageType(),
                        userId = this.userId,
                        isFirst = true,
                        isLast = true,
                        message = botData.data.toModels().toImmutableList(),
                        visibleMessage = visibleMessage,
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
                    val botData = message.toResponse<MessageBotRawKeywordInData<List<TimetableResponse>>>()

                    val response = botData.data.toModels().toImmutableList()

                    val responseToString = response.map { "${it.time}교시 : ${it.subject}" }

                    MessageRoomEvent.MessageParent.BOT.Timetable(
                        id = id,
                        chatRoomId = chatRoomId,
                        type = type.toMessageType(),
                        userId = this.userId,
                        isFirst = true,
                        isLast = true,
                        message = response,
                        visibleMessage = "오늘의 시간표에요\n${responseToString.joinToString("\n")}",
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
                    val response = botData.data.toModels().toImmutableList()

                    var visibleMessage = ""

                    response.forEach {
                        visibleMessage += "${it.userName} 선생님이 공지를 작성하셨어요\n" +
                            "제목: ${it.title}" +
                            it.content
                    }

                    MessageRoomEvent.MessageParent.BOT.Notification(
                        id = id,
                        chatRoomId = chatRoomId,
                        type = type.toMessageType(),
                        userId = this.userId,
                        isFirst = true,
                        isLast = true,
                        message = botData.data.toModels().toImmutableList(),
                        visibleMessage = visibleMessage,
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
                "팀짜기" -> {
                    val botData = message.toResponse<MessageBotRawKeywordInData<String>>()
                    MessageRoomEvent.MessageParent.BOT.TeamBuild(
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
                "기타" -> {
                    val botData = message.toResponse<MessageBotRawKeywordInData<String>>()
                    MessageRoomEvent.MessageParent.BOT.Etc(
                        id = id,
                        chatRoomId = chatRoomId,
                        type = type.toMessageType(),
                        userId = this.userId,
                        isFirst = true,
                        isLast = true,
                        message = botData.data,
                        messageStatus = messageStatus,
                        emoticon = emoticon,
                        eventList = eventList?.toImmutableList() ?: persistentListOf(),
                        emojiList = emojiList.map { it.toModel() }.toImmutableList(),
                        mention = mention.toImmutableList(),
                        mentionAll = mentionAll,
                        timestamp = timestamp,
                    )
                }
                else ->
                    MessageRoomEvent.MessageParent.BOT.Etc(
                        id = id,
                        chatRoomId = chatRoomId,
                        type = type.toMessageType(),
                        userId = this.userId,
                        isFirst = true,
                        isLast = true,
                        message = "현재 버전에서 지원하지 않는 메세지 유형입니다.",
                        messageStatus = messageStatus,
                        emoticon = emoticon,
                        eventList = eventList?.toImmutableList() ?: persistentListOf(),
                        emojiList = emojiList.map { it.toModel() }.toImmutableList(),
                        mention = mention.toImmutableList(),
                        mentionAll = mentionAll,
                        timestamp = timestamp,
                    )
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
