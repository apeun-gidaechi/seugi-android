package com.apeun.gidaechi.data.core.mapper

import com.apeun.gidaechi.data.core.model.UserModel
import com.apeun.gidaechi.network.core.response.UserResponse

fun UserResponse.toModel() = UserModel(
    id = id,
    email = email,
    birth = birth,
    name = name,
    picture = picture,
)
