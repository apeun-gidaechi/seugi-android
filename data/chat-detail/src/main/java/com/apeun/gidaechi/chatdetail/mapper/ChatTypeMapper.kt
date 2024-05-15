package com.apeun.gidaechi.chatdetail.mapper

import com.apeun.gidaechi.chatdetail.model.ChatType
import com.apeun.gidaechi.network.chatdetail.response.ChatDetailTypeResponse
import com.apeun.gidaechi.network.chatdetail.response.emoji.ChatDetailEmojiResponse
import com.apeun.gidaechi.network.chatdetail.response.message.ChatDetailMessageDeleteResponse
import com.apeun.gidaechi.network.chatdetail.response.message.ChatDetailMessageResponse
import com.apeun.gidaechi.network.chatdetail.response.sub.ChatDetailSubResponse

internal fun String.toChatType(): ChatType =
    when(this) {
        "MESSAGE" -> ChatType.MESSAGE
        "FILE" -> ChatType.FILE
        "IMG" -> ChatType.IMG
        "ENTER" -> ChatType.ENTER
        "LEFT" -> ChatType.LEFT
        "SUB" -> ChatType.SUB
        "DELETE_MESSAGE" -> ChatType.DELETE_MESSAGE
        "ADD_EMOJI" -> ChatType.ADD_EMOJI
        "REMOVE_EMOJI" -> ChatType.REMOVE_EMOJI
        else -> ChatType.MESSAGE
    }

internal fun ChatDetailTypeResponse.toModel() =
    when (this.type) {
        "MESSAGE", "FILE", "IMG", "ENTER", "LEFT" -> (this as ChatDetailMessageResponse).toModel()
        "SUB" -> (this as ChatDetailSubResponse).toModel()
        "DELETE_MESSAGE" -> (this as ChatDetailMessageDeleteResponse).toModel()
        "ADD_EMOJI", "REMOVE_EMOJI" -> (this as ChatDetailEmojiResponse).toModel()
        else -> {
            throw RuntimeException()
        }
    }