package com.apeun.gidaechi.chatdetail.mapper

import com.apeun.gidaechi.chatdetail.model.ChatDetailEmojiModel
import com.apeun.gidaechi.chatdetail.model.ChatDetailUserModel
import com.apeun.gidaechi.network.chatdetail.response.ChatDetailEmojiResponse
import com.apeun.gidaechi.network.chatdetail.response.ChatDetailUserResponse

fun ChatDetailUserResponse.toModel() =
    ChatDetailUserModel(
        id = id,
        name = name
    )