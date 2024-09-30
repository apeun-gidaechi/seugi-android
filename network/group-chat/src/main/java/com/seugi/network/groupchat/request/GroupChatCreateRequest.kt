package com.seugi.network.personalchat.request

data class GroupChatCreateRequest(
    val workspaceId: String,
    val roomName: String,
    val joinUsers: List<Int>,
    val chatRoomImg: String,
)
