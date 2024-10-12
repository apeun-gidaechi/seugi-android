package com.seugi.data.message.mapper

import com.seugi.data.message.model.MessageRoomEvent
import com.seugi.network.message.response.MessageRoomEventResponse
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

internal fun MessageRoomEventResponse.toEventModel(userId: Int): MessageRoomEvent = when (this) {
    is MessageRoomEventResponse.MessageParent.Message -> toModel(userId)
    is MessageRoomEventResponse.DeleteMessage -> toModel()
    is MessageRoomEventResponse.AddEmoji -> toModel()
    is MessageRoomEventResponse.Raw -> throw IllegalArgumentException("Not Invalid MessageRoomEventResponse.Raw Type")
    is MessageRoomEventResponse.RemoveEmoji -> toModel()
    is MessageRoomEventResponse.Sub -> toModel()
    is MessageRoomEventResponse.TransperAdmin -> toModel()
}

internal fun MessageRoomEventResponse.MessageParent.Message.toModel(userId: Int): MessageRoomEvent.MessageParent = when (type) {
    "MESSAGE" -> {
        if (userId == this.userId) {
            MessageRoomEvent.MessageParent.Me(
                id = id,
                chatRoomId = chatRoomId,
                type = type.toMessageType(),
                userId = userId,
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
                userId = userId,
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
            userId = userId,
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
            userId = userId,
            uuid = uuid,
        )
    }

    "ENTER" -> {
        MessageRoomEvent.MessageParent.Enter(
            type = type.toMessageType(),
            userId = userId,
            timestamp = timestamp,
            roomId = chatRoomId,
            eventList = eventList?.toImmutableList() ?: persistentListOf(),
        )
    }
    "LEFT" -> {
        MessageRoomEvent.MessageParent.Left(
            type = type.toMessageType(),
            userId = userId,
            timestamp = timestamp,
            roomId = chatRoomId,
            eventList = eventList?.toImmutableList() ?: persistentListOf(),
        )
    }

    else -> throw IllegalArgumentException("Not Invalid MessageRoomEventResponse.Raw Type")
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

internal fun MessageRoomEventResponse.TransperAdmin.toModel() = MessageRoomEvent.TransperAdmin(
    type = type.toMessageType(),
    userId = userId,
    roomId = roomId,
    eventList = eventList.toImmutableList(),
)
