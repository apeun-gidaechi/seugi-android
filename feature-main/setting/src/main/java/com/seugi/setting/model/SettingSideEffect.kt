package com.seugi.setting.model

sealed interface SettingSideEffect {
    data object SuccessLogout : SettingSideEffect
    data object SuccessWithdraw : SettingSideEffect
    data class FailedWithdraw(val throwable: Throwable) : SettingSideEffect
}
