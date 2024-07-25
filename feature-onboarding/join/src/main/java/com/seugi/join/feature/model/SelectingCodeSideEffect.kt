package com.seugi.join.feature.model

sealed class SelectingCodeSideEffect {
    data object SuccessApplication : SelectingCodeSideEffect()
    data class FiledApplication(val throwable: Throwable) : SelectingCodeSideEffect()
}
