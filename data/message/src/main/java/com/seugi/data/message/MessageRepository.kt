package com.seugi.data.message

import com.seugi.common.model.Result
import com.seugi.data.message.model.MessageLoadModel
import com.seugi.data.message.model.MessageRoomEvent
import com.seugi.data.message.model.MessageType
import com.seugi.data.message.model.stomp.MessageStompLifecycleModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface MessageRepository {

    suspend fun sendMessage(chatRoomId: String, message: String, messageUUID: String, type: MessageType): Result<Boolean>

    suspend fun subscribeRoom(chatRoomId: String, userId: Int): Flow<Result<MessageRoomEvent>>

    suspend fun reSubscribeRoom(chatRoomId: String, userId: Int): Flow<Result<MessageRoomEvent>>

    suspend fun getMessage(chatRoomId: String, timestamp: LocalDateTime?, userId: Int): Flow<Result<MessageLoadModel>>

    suspend fun collectStompLifecycle(): Flow<Result<MessageStompLifecycleModel>>
}
