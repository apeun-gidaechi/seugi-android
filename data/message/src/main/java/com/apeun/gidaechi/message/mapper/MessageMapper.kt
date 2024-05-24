package com.apeun.gidaechi.message.mapper

import com.apeun.gidaechi.message.model.message.MessageMessageDeleteModel
import com.apeun.gidaechi.message.model.message.MessageEmojiModel
import com.apeun.gidaechi.message.model.message.MessageLoadModel
import com.apeun.gidaechi.message.model.message.MessageMessageModel
import com.apeun.gidaechi.message.model.message.MessageUserModel
import com.apeun.gidaechi.network.chatdetail.response.message.ChatDetailMessageDeleteResponse
import com.apeun.gidaechi.network.chatdetail.response.message.ChatDetailMessageEmojiResponse
import com.apeun.gidaechi.network.chatdetail.response.message.ChatDetailMessageLoadResponse
import com.apeun.gidaechi.network.chatdetail.response.message.ChatDetailMessageResponse
import com.apeun.gidaechi.network.chatdetail.response.message.ChatDetailMessageUserResponse

internal fun ChatDetailMessageResponse.toModel() =
    MessageMessageModel(
        id = id,
        chatRoomId = chatRoomId,
        type = type.toMessageType(),
        author = author.toModel(),
        message = message,
        emojiList = emojiList.toModels(),
        mention = mention,
        mentionAll = mentionAll,
        timestamp = timestamp,
        read = read,
        joined = joined,
        messageStatus = messageStatus.toMessageLifeType()
    )

@JvmName("ListChatDetailMessageResponseToModels")
internal fun List<ChatDetailMessageResponse>.toModels() =
    this.map {
        it.toModel()
    }


internal fun ChatDetailMessageDeleteResponse.toModel() =
    MessageMessageDeleteModel(
        type = type.toMessageType(),
        eventList = eventList,
        messageId = messageId,
    )

internal fun ChatDetailMessageEmojiResponse.toModel() =
    MessageEmojiModel(
        emojiId = emojiId,
        userId = userId
    )

@JvmName("ListChatDetailMessageEmojiResponseToModels")
internal fun List<ChatDetailMessageEmojiResponse>.toModels() =
    this.map {
        it.toModel()
    }

internal fun ChatDetailMessageUserResponse.toModel() =
    MessageUserModel(
        id = id,
        name = name,
        profile = null
    )

internal fun ChatDetailMessageLoadResponse.toModel() =
    MessageLoadModel(
        firstMessageId = firstMessageId,
        messages = messages.toModels()
    )