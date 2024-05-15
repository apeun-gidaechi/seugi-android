package com.apeun.gidaechi.chatdetail.model.emoji

import com.apeun.gidaechi.chatdetail.model.ChatDetailTypeModel
import com.apeun.gidaechi.chatdetail.model.ChatType

data class ChatDetailEmojiModel(
    override val type: ChatType,
    val eventList: List<Int>,
    val messageId: String,
    val emojiId : Int
): ChatDetailTypeModel()
