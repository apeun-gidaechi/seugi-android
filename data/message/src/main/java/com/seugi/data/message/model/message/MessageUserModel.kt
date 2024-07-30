package com.seugi.data.message.model.message

data class MessageUserModel(
    val id: Int,
    val name: String,
    val profile: String?,
) {
    constructor(id: Int) : this(id, "", null)
}