package com.apeun.gidaechi.chatdetail.mapper

import com.apeun.gidaechi.chatdetail.model.ChatDetailEmojiModel
import com.apeun.gidaechi.chatdetail.model.ChatDetailMessageModel
import com.apeun.gidaechi.chatdetail.model.ChatDetailUserModel
import com.apeun.gidaechi.chatdetail.model.ChatMessageType
import com.apeun.gidaechi.network.chatdetail.response.ChatDetailEmojiResponse
import com.apeun.gidaechi.network.chatdetail.response.ChatDetailMessageResponse
import com.apeun.gidaechi.network.chatdetail.response.ChatDetailUserResponse
import java.time.LocalDateTime

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