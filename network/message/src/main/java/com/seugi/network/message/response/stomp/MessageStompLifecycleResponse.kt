package com.seugi.network.message.response.stomp

sealed interface MessageStompLifecycleResponse {
    data object Open : MessageStompLifecycleResponse
    data object Closed : MessageStompLifecycleResponse
    data object FailedServerHeartbeat : MessageStompLifecycleResponse
    data class Error(val message: String) : MessageStompLifecycleResponse
}