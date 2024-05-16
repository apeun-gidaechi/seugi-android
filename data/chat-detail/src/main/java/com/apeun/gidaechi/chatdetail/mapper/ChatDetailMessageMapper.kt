package com.apeun.gidaechi.chatdetail.mapper

import com.apeun.gidaechi.chatdetail.model.message.ChatDetailMessageDeleteModel
import com.apeun.gidaechi.chatdetail.model.message.ChatDetailMessageEmojiModel
import com.apeun.gidaechi.chatdetail.model.message.ChatDetailMessageLoadModel
import com.apeun.gidaechi.chatdetail.model.message.ChatDetailMessageModel
import com.apeun.gidaechi.chatdetail.model.message.ChatDetailMessageUserModel
import com.apeun.gidaechi.network.chatdetail.response.message.ChatDetailMessageDeleteResponse
import com.apeun.gidaechi.network.chatdetail.response.message.ChatDetailMessageEmojiResponse
import com.apeun.gidaechi.network.chatdetail.response.message.ChatDetailMessageLoadResponse
import com.apeun.gidaechi.network.chatdetail.response.message.ChatDetailMessageResponse
import com.apeun.gidaechi.network.chatdetail.response.message.ChatDetailMessageUserResponse

internal fun ChatDetailMessageResponse.toModel() =
    ChatDetailMessageModel(
        id = id,
        chatRoomId = chatRoomId,
        type = type.toChatType(),
        author = author.toModel(),
        message = message,
        emojiList = emojiList.toModels(),
        mention = mention,
        mentionAll = mentionAll,
        timestamp = timestamp,
        read = read,
        joined = joined,
        messageStatus = messageStatus.toChatMessageType()
    )

@JvmName("ListChatDetailMessageResponseToModels")
internal fun List<ChatDetailMessageResponse>.toModels() =
    this.map {
        it.toModel()
    }


internal fun ChatDetailMessageDeleteResponse.toModel() =
    ChatDetailMessageDeleteModel(
        type = type.toChatType(),
        eventList = eventList,
        messageId = messageId,
    )

internal fun ChatDetailMessageEmojiResponse.toModel() =
    ChatDetailMessageEmojiModel(
        emojiId = emojiId,
        userId = userId
    )

@JvmName("ListChatDetailMessageEmojiResponseToModels")
internal fun List<ChatDetailMessageEmojiResponse>.toModels() =
    this.map {
        it.toModel()
    }

internal fun ChatDetailMessageUserResponse.toModel() =
    ChatDetailMessageUserModel(
        id = id,
        name = name
    )

internal fun ChatDetailMessageLoadResponse.toModel() =
    ChatDetailMessageLoadModel(
        firstMessageId = firstMessageId,
        messages = messages.toModels()
    )