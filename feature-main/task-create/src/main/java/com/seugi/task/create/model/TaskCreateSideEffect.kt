package com.seugi.task.create.model

import java.lang.Exception

sealed interface TaskCreateSideEffect {
    data object Success: TaskCreateSideEffect
    data class Failed(val throwable: Throwable): TaskCreateSideEffect
}