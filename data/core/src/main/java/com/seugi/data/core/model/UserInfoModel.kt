package com.seugi.data.core.model

import java.time.LocalDateTime

data class UserInfoModel(
    val userInfo: UserModel,
    val timestamp: LocalDateTime,
    val utcTimeMillis: Long,
)
