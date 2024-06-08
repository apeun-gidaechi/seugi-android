package com.apeun.gidaechi.network.message

import com.apeun.gidaechi.network.message.response.MessageTypeResponse
import com.apeun.gidaechi.network.message.response.message.MessageLoadResponse
import com.apeun.gidaechi.network.message.response.room.MessageRoomResponse
import com.apeun.gidaechi.network.message.response.room.MessageRoomMemberResponse
import com.apeun.gidaechi.network.core.response.BaseResponse
import kotlinx.coroutines.flow.Flow

interface MessageDataSource {
    suspend fun subscribeRoom(chatRoomId: String): Flow<MessageTypeResponse>

    suspend fun sendMessage(chatRoomId: String, message: String): Boolean

    suspend fun connectStomp(accessToken: String)

    suspend fun reConnectStomp(accessToken: String)

    suspend fun getIsConnect(): Boolean

    suspend fun getMessage(chatRoomId: String, page: Int, size: Int): BaseResponse<MessageLoadResponse>

    suspend fun loadRoomInfo(isPersonal: Boolean, roomId: String): BaseResponse<MessageRoomResponse>

    suspend fun loadRoomMember(roomId: String):  BaseResponse<MessageRoomMemberResponse>

    suspend fun leftRoom(chatRoomId: String): BaseResponse<Unit?>
}