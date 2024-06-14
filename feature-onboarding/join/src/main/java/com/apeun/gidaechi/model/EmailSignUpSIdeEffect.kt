package com.apeun.gidaechi.model

sealed class EmailSignUpSIdeEffect {
    data object Success: EmailSignUpSIdeEffect()

    data class FailedJoin(val throwable: Throwable): EmailSignUpSIdeEffect()
}