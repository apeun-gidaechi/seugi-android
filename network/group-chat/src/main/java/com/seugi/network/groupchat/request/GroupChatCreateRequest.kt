package com.seugi.network.personalchat.request

data class GroupChatCreateRequest(
    val workspaceId: String,
    val roomName: String,
    val joinUsers: List<Long>,
    val chatRoomImg: String,
)
