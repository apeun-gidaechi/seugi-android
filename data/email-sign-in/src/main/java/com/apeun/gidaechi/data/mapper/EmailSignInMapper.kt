package com.apeun.gidaechi.data.mapper

import com.apeun.gidaechi.data.model.EmailSignInModel
import com.apeun.gidaechi.network.response.EmailSignInResponse

internal fun EmailSignInResponse.toModel() =
    EmailSignInModel(
        accessToken = accessToken,
        refreshToken = refreshToken
    )