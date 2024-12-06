package com.seugi.network.message

import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.Response
import com.seugi.network.message.response.MessageRoomEventResponse
import com.seugi.network.message.response.message.MessageLoadResponse
import com.seugi.network.message.response.stomp.MessageStompErrorResponse
import com.seugi.network.message.response.stomp.MessageStompLifecycleResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface MessageDataSource {
    suspend fun subscribeRoom(chatRoomId: String): Flow<MessageRoomEventResponse>

    suspend fun subscribeError(): Flow<MessageStompErrorResponse>

    suspend fun sendMessage(chatRoomId: String, message: String, messageUUID: String, type: String, mention: List<Long>): Boolean

    suspend fun connectStompSocket(accessToken: String)

    suspend fun reConnectStompSocket(accessToken: String, refreshToken: String)

    suspend fun closeStompSocket()

    suspend fun getIsConnect(): Boolean

    suspend fun getMessage(chatRoomId: String, timestamp: LocalDateTime?): BaseResponse<MessageLoadResponse>

    suspend fun collectStompLifecycle(): Flow<MessageStompLifecycleResponse>

    suspend fun sendText(text: String): BaseResponse<String>

    suspend fun putEmoji(messageId: String, roomId: String, emojiId: Int): Response

    suspend fun deleteEmoji(messageId: String, roomId: String, emojiId: Int): Response
}
