package com.seugi.setting.model

sealed interface SettingSideEffect {
    data object SuccessLogout: SettingSideEffect
}