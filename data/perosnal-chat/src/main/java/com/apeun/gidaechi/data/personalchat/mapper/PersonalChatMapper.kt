package com.apeun.gidaechi.data.personalchat.mapper

import com.apeun.gidaechi.data.core.mapper.toModel
import com.apeun.gidaechi.data.personalchat.model.PersonalChatRoomModel
import com.apeun.gidaechi.data.personalchat.model.PersonalChatRoomStatusType
import com.apeun.gidaechi.data.personalchat.model.PersonalChatRoomType
import com.apeun.gidaechi.data.personalchat.response.PersonalChatRoomResponse

internal fun List<PersonalChatRoomResponse>.toModels() = this.map {
    it.toModel()
}

internal fun PersonalChatRoomResponse.toModel() = PersonalChatRoomModel(
    id = id,
    workspaceId = workspaceId,
    type = type.toPersonalChatRoomType(),
    roomAdmin = roomAdmin,
    chatName = chatName,
    chatRoomImg = chatRoomImg,
    createdAt = createdAt,
    memberList = joinUserId.map {
        it.toModel()
    },
    chatStatusEnum = chatStatusEnum.toPersonalChatRoomStatusType(),
    lastMessage = lastMessage,
    lastMessageTimestamp = lastMessageTimestamp,
    notReadCnt = notReadCnt,
)

internal fun String.toPersonalChatRoomType() = when (this) {
    "PERSONAL" -> PersonalChatRoomType.PERSONAL
    else -> PersonalChatRoomType.GROUP
}

internal fun String.toPersonalChatRoomStatusType() = when (this) {
    "ALIVE" -> PersonalChatRoomStatusType.ALIVE
    else -> PersonalChatRoomStatusType.DELETE
}