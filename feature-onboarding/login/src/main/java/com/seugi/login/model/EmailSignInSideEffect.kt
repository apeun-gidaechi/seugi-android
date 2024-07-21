package com.seugi.login.model

sealed class EmailSignInSideEffect {
    data class FailedLogin(val throwable: Throwable) : EmailSignInSideEffect()
    data object SuccessLogin : EmailSignInSideEffect()
}
