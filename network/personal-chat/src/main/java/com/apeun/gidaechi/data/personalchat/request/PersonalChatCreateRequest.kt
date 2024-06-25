package com.apeun.gidaechi.data.personalchat.request

data class PersonalChatCreateRequest(
    val workspaceId: String,
    val roomName: String,
    val joinUsers: List<Int>,
    val chatRoomImg: String,
)
