package com.apeun.gidaechi.model

sealed class EmailSignUpSideEffect {
    data object Success : EmailSignUpSideEffect()
    data object FailedVeritication: EmailSignUpSideEffect()
    data object FailedJoin : EmailSignUpSideEffect()
}
