package com.seugi.data.core.mapper

import com.seugi.common.utiles.toDeviceLocalDateTime
import com.seugi.common.utiles.toEpochMilli
import com.seugi.data.core.model.UserInfoModel
import com.seugi.data.core.model.UserModel
import com.seugi.network.core.response.UserInfoResponse
import com.seugi.network.core.response.UserResponse

fun UserResponse.toModel() = UserModel(
    id = id,
    email = email,
    birth = birth,
    name = name,
    picture = picture,
)

fun UserInfoResponse.toModel() = UserInfoModel(
    timestamp = timestamp,
    userInfo = userInfo.toModel(),
    utcTimeMillis = timestamp.toDeviceLocalDateTime().toEpochMilli(),
)

fun List<UserResponse>.toModels() = this.map{
    it.toModel()
}
