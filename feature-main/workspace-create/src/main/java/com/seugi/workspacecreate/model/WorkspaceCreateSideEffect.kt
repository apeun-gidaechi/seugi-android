package com.seugi.workspacecreate.model

sealed interface WorkspaceCreateSideEffect {
    data object SuccessCreate : WorkspaceCreateSideEffect
    data class Error(val throwable: Throwable) : WorkspaceCreateSideEffect
}
