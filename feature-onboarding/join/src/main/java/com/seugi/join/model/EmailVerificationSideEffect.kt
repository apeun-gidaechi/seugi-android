package com.seugi.join.model

sealed class EmailVerificationSideEffect {
    data object SuccessGetCode : EmailVerificationSideEffect()
    data object SuccessJoin : EmailVerificationSideEffect()
    data object FiledJoin : EmailVerificationSideEffect()
    data object Error : EmailVerificationSideEffect()
}
