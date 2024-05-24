package com.apeun.gidaechi.network.message

import com.apeun.gidaechi.network.message.response.MessageTypeResponse
import com.apeun.gidaechi.network.message.response.message.MessageLoadResponse
import com.apeun.gidaechi.network.message.response.room.MessageRoomResponse
import com.apeun.gidaechi.network.message.response.room.MessageRoomMemberResponse
import com.apeun.gidaechi.network.core.response.BaseResponse
import kotlinx.coroutines.flow.Flow

interface MessageDataSource {
    suspend fun subscribeRoom(chatRoomId: Int): Flow<MessageTypeResponse>

    suspend fun sendMessage(chatRoomId: Int, message: String): Boolean

    suspend fun connectStomp(accessToken: String)

    suspend fun reConnectStomp(accessToken: String)

    suspend fun getIsConnect(): Boolean

    suspend fun getMessage(chatRoomId: Int, page: Int, size: Int): BaseResponse<MessageLoadResponse>

    suspend fun loadRoomInfo(isPersonal: Boolean, roomId: Int): BaseResponse<MessageRoomResponse>

    suspend fun loadRoomMember(roomId: Int):  BaseResponse<MessageRoomMemberResponse>

}