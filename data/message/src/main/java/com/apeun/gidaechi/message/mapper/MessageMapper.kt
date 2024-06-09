package com.apeun.gidaechi.message.mapper

import com.apeun.gidaechi.message.model.message.MessageMessageDeleteModel
import com.apeun.gidaechi.message.model.message.MessageEmojiModel
import com.apeun.gidaechi.message.model.message.MessageLoadModel
import com.apeun.gidaechi.message.model.message.MessageMessageModel
import com.apeun.gidaechi.message.model.message.MessageUserModel
import com.apeun.gidaechi.network.message.response.message.MessageDeleteResponse
import com.apeun.gidaechi.network.message.response.message.MessageEmojiResponse
import com.apeun.gidaechi.network.message.response.message.MessageLoadResponse
import com.apeun.gidaechi.network.message.response.message.MessageMessageResponse
import com.apeun.gidaechi.network.message.response.message.MessageUserResponse

internal fun MessageMessageResponse.toModel() =
    MessageMessageModel(
        id = id,
        chatRoomId = chatRoomId,
        type = type.toMessageType(),
        author = userId,
        message = message,
        emojiList = emojiList.toModels(),
        mention = mention,
        mentionAll = mentionAll,
        timestamp = timestamp,
        read = read,
        messageStatus = messageStatus.toMessageLifeType()
    )

@JvmName("ListChatDetailMessageResponseToModels")
internal fun List<MessageMessageResponse>.toModels() =
    this.map {
        it.toModel()
    }


internal fun MessageDeleteResponse.toModel() =
    MessageMessageDeleteModel(
        type = type.toMessageType(),
        eventList = eventList,
        messageId = messageId,
    )

internal fun MessageEmojiResponse.toModel() =
    MessageEmojiModel(
        emojiId = emojiId,
        userId = userId
    )

@JvmName("ListChatDetailMessageEmojiResponseToModels")
internal fun List<MessageEmojiResponse>.toModels() =
    this.map {
        it.toModel()
    }

internal fun MessageUserResponse.toModel() =
    MessageUserModel(
        id = id,
        name = name,
        profile = null
    )

internal fun MessageLoadResponse.toModel() =
    MessageLoadModel(
        firstMessageId = firstMessageId,
        messages = messages.toModels()
    )