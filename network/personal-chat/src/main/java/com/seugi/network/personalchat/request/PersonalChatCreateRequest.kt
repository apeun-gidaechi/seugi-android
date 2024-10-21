package com.seugi.network.personalchat.request

data class PersonalChatCreateRequest(
    val workspaceId: String,
    val roomName: String,
    val joinUsers: List<Long>,
    val chatRoomImg: String,
)
