package com.seugi.network.personalchat.request

data class GroupChatMemberAddRequest(
    val chatRoomId: String,
    val chatMemberUsers: List<Long>,
)
