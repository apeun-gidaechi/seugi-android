package com.apeun.gidaechi.chatdetail.mapper

import com.apeun.gidaechi.chatdetail.model.room.ChatRoomModel
import com.apeun.gidaechi.chatdetail.model.room.ChatRoomStatusType
import com.apeun.gidaechi.chatdetail.model.room.ChatRoomType
import com.apeun.gidaechi.network.chatdetail.response.room.ChatDetailChatRoomResponse

internal fun ChatDetailChatRoomResponse.toModel() =
    ChatRoomModel(
        id = id,
        type = type.toRoomType(),
        chatName = chatName,
        containUserCnt = containUserCnt,
        chatRoomImg = chatRoomImg,
        createdAt = createdAt,
        chatStatusEnum = chatStatusEnum.toRoomStatus()
    )

internal fun String.toRoomType() =
    when(this) {
        "PERSONAL" -> ChatRoomType.PERSONAL
        else -> ChatRoomType.GROUP
    }

internal fun String.toRoomStatus() =
    when(this) {
        "ALIVE" -> ChatRoomStatusType.ALIVE
        else -> ChatRoomStatusType.DELETE
    }