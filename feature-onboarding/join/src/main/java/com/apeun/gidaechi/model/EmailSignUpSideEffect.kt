package com.apeun.gidaechi.model

sealed class EmailSignUpSideEffect {
    data object Success : EmailSignUpSideEffect()

    data class FailedJoin(val throwable: Throwable) : EmailSignUpSideEffect()
}
