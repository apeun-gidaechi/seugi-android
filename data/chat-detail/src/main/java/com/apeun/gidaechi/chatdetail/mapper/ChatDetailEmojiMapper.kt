package com.apeun.gidaechi.chatdetail.mapper

import com.apeun.gidaechi.chatdetail.model.ChatDetailEmojiModel
import com.apeun.gidaechi.network.chatdetail.response.ChatDetailEmojiResponse

internal fun ChatDetailEmojiResponse.toModel() =
    ChatDetailEmojiModel(
        emojiId = emojiId,
        userId = userId
    )

internal fun List<ChatDetailEmojiResponse>.toModels() =
    this.map {
        it.toModel()
    }