package com.seugi.notification.model

sealed interface NotificationSideEffect {
    data class Error(val throwable: Throwable): NotificationSideEffect
}