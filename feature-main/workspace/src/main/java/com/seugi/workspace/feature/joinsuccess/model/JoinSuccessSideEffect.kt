package com.seugi.workspace.feature.joinsuccess.model

sealed class JoinSuccessSideEffect {
    data object SuccessApplication : JoinSuccessSideEffect()
    data class FiledApplication(val throwable: Throwable) : JoinSuccessSideEffect()
}
