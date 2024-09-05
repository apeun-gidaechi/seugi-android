package com.seugi.data.message.mapper

import com.seugi.data.message.model.stomp.MessageStompLifecycleModel
import com.seugi.network.message.response.stomp.MessageStompLifecycleResponse

internal fun MessageStompLifecycleResponse.toModel() = when (this) {
    is MessageStompLifecycleResponse.Error -> MessageStompLifecycleModel.Error(this.throwable)
    is MessageStompLifecycleResponse.Open -> MessageStompLifecycleModel.Open
    is MessageStompLifecycleResponse.FailedServerHeartbeat -> MessageStompLifecycleModel.FailedServerHeartbeat
    is MessageStompLifecycleResponse.Closed -> MessageStompLifecycleModel.Closed
}
