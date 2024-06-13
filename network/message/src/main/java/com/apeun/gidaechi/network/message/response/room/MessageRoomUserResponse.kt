package com.apeun.gidaechi.network.message.response.room

data class MessageRoomUserResponse(
    val id: Int,
    val email: String,
    val birth: String,
    val name: String,
    val picture: String,
)
