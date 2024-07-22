package com.seugi.data.mapper

import com.seugi.data.model.EmailSignInModel
import com.seugi.network.response.EmailSignInResponse

internal fun EmailSignInResponse.toModel() = EmailSignInModel(
    accessToken = accessToken,
    refreshToken = refreshToken,
)
