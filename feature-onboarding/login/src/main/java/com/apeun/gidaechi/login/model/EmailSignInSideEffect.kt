package com.apeun.gidaechi.login.model

sealed class EmailSignInSideEffect {
    data class FailedLogin(val throwable: Throwable) : EmailSignInSideEffect()
    data object SuccessLogin : EmailSignInSideEffect()
}
