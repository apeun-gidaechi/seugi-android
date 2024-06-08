package com.apeun.gidaechi.message

import com.apeun.gidaechi.message.model.MessageTypeModel
import com.apeun.gidaechi.message.model.message.MessageLoadModel
import com.apeun.gidaechi.message.model.room.MessageRoomModel
import com.apeun.gidaechi.common.model.Result
import kotlinx.coroutines.flow.Flow


interface MessageRepository {

    suspend fun sendMessage(chatRoomId: String, message: String): Result<Boolean>

    suspend fun subscribeRoom(chatRoomId: String): Flow<Result<MessageTypeModel>>

    suspend fun reSubscribeRoom(chatRoomId: String): Flow<Result<MessageTypeModel>>

    suspend fun getMessage(chatRoomId: String, page: Int, size: Int): Flow<Result<MessageLoadModel>>

    suspend fun loadRoomInfo(isPersonal: Boolean, roomId: String): Flow<Result<MessageRoomModel>>

    suspend fun leftRoom(chatRoomId: String): Flow<Result<Unit>>
}