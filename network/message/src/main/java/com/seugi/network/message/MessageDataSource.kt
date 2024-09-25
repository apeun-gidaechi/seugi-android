package com.seugi.network.message

import com.seugi.network.core.response.BaseResponse
import com.seugi.network.message.response.MessageRoomEventResponse
import com.seugi.network.message.response.MessageTypeResponse
import com.seugi.network.message.response.message.MessageLoadResponse
import com.seugi.network.message.response.room.MessageRoomMemberResponse
import com.seugi.network.message.response.room.MessageRoomResponse
import com.seugi.network.message.response.stomp.MessageStompLifecycleResponse
import kotlinx.coroutines.flow.Flow

interface MessageDataSource {
    suspend fun subscribeRoom(chatRoomId: String): Flow<MessageRoomEventResponse>

    suspend fun sendMessage(chatRoomId: String, message: String, messageUUID: String, type: String): Boolean

    suspend fun connectStomp(accessToken: String)

    suspend fun reConnectStomp(accessToken: String, refreshToken: String)

    suspend fun getIsConnect(): Boolean

    suspend fun getMessage(chatRoomId: String, page: Int, size: Int): BaseResponse<MessageLoadResponse>

    suspend fun collectStompLifecycle(): Flow<MessageStompLifecycleResponse>
}
