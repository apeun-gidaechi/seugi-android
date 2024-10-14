package com.seugi.data.message.model.stomp

sealed interface MessageStompLifecycleModel {
    data object Open : MessageStompLifecycleModel
    data object Closed : MessageStompLifecycleModel
    data object FailedServerHeartbeat : MessageStompLifecycleModel
    data object Connected : MessageStompLifecycleModel
    data class Error(val exception: Exception) : MessageStompLifecycleModel
}
