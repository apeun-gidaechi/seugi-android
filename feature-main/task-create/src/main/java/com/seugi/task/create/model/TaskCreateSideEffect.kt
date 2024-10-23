package com.seugi.task.create.model

sealed interface TaskCreateSideEffect {
    data object Success : TaskCreateSideEffect
    data class Failed(val throwable: Throwable) : TaskCreateSideEffect
}
