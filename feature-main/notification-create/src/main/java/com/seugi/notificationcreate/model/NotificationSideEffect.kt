package com.seugi.notificationcreate.model

sealed interface NotificationSideEffect {
    data object Success: NotificationSideEffect
    data class Error(val throwable: Throwable): NotificationSideEffect
}