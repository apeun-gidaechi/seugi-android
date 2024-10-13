package com.seugi.network.core.response

import java.time.LocalDateTime

data class UserInfoResponse(
    val userInfo: UserResponse,
    val timestamp: LocalDateTime,
)
