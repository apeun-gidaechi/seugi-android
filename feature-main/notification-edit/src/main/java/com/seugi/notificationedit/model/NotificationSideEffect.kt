package com.seugi.notificationedit.model

sealed interface NotificationSideEffect {
    data class Success(val message: String) : NotificationSideEffect
    data class Error(val throwable: Throwable) : NotificationSideEffect
}
