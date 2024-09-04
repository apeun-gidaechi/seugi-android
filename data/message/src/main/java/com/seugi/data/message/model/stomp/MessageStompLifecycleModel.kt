package com.seugi.data.message.model.stomp

sealed interface MessageStompLifecycleModel {
    data object Open : MessageStompLifecycleModel
    data object Closed : MessageStompLifecycleModel
    data object FailedServerHeartbeat : MessageStompLifecycleModel
    data class Error(val throwable: Throwable) : MessageStompLifecycleModel
}
