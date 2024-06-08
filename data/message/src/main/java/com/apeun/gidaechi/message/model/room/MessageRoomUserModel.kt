package com.apeun.gidaechi.message.model.room

data class MessageRoomUserModel(
    val id: Int,
    val email: String,
    val birth: String,
    val name: String,
    val picture: String
)