package com.seugi.network.message.response.stomp

import java.lang.Exception

sealed interface MessageStompLifecycleResponse {
    data object Open : MessageStompLifecycleResponse
    data object Closed : MessageStompLifecycleResponse
    data object FailedServerHeartbeat : MessageStompLifecycleResponse
    data object Connected : MessageStompLifecycleResponse
    data class Error(val exception: Exception) : MessageStompLifecycleResponse
}
