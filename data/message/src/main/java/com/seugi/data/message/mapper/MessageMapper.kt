package com.seugi.data.message.mapper

import com.seugi.data.message.model.message.MessageEmojiModel
import com.seugi.data.message.model.message.MessageLoadModel
import com.seugi.data.message.model.message.MessageMessageDeleteModel
import com.seugi.data.message.model.message.MessageMessageModel
import com.seugi.data.message.model.message.MessageUserModel
import com.seugi.network.message.response.message.MessageDeleteResponse
import com.seugi.network.message.response.message.MessageEmojiResponse
import com.seugi.network.message.response.message.MessageLoadResponse
import com.seugi.network.message.response.message.MessageMessageResponse
import com.seugi.network.message.response.message.MessageUserResponse
import kotlinx.collections.immutable.toImmutableList

internal fun MessageMessageResponse.toModel() = MessageMessageModel(
    id = id,
    chatRoomId = chatRoomId,
    type = type.toMessageType(),
    author = userId,
    message = message,
    emojiList = emojiList.toModels().toImmutableList(),
    mention = mention.toImmutableList(),
    mentionAll = mentionAll,
    timestamp = timestamp,
    read = read.toImmutableList(),
    messageStatus = messageStatus.toMessageLifeType(),
)

@JvmName("ListChatDetailMessageResponseToModels")
internal fun List<MessageMessageResponse>.toModels() = this.map {
    it.toModel()
}

internal fun MessageDeleteResponse.toModel() = MessageMessageDeleteModel(
    type = type.toMessageType(),
    eventList = eventList.toImmutableList(),
    messageId = messageId,
)

internal fun MessageEmojiResponse.toModel() = MessageEmojiModel(
    emojiId = emojiId,
    userId = userId.toImmutableList(),
)

@JvmName("ListChatDetailMessageEmojiResponseToModels")
internal fun List<MessageEmojiResponse>.toModels() = this.map {
    it.toModel()
}

internal fun MessageUserResponse.toModel() = MessageUserModel(
    id = id,
    name = name,
    profile = null,
)

internal fun MessageLoadResponse.toModel() = MessageLoadModel(
    firstMessageId = firstMessageId,
    messages = messages.toModels().toImmutableList(),
)
