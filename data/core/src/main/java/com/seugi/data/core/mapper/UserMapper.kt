package com.seugi.data.core.mapper

import com.seugi.data.core.model.UserModel
import com.seugi.network.core.response.UserResponse

fun UserResponse.toModel() = UserModel(
    id = id,
    email = email,
    birth = birth,
    name = name,
    picture = picture,
)
