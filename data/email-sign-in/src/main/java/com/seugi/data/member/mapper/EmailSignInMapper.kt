package com.seugi.data.member.mapper

import com.seugi.data.member.model.EmailSignInModel
import com.seugi.network.response.EmailSignInResponse

internal fun EmailSignInResponse.toModel() = EmailSignInModel(
    accessToken = accessToken,
    refreshToken = refreshToken,
)
