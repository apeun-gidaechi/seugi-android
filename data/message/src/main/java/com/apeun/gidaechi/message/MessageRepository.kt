package com.apeun.gidaechi.message

import com.apeun.gidaechi.message.model.MessageTypeModel
import com.apeun.gidaechi.message.model.message.MessageLoadModel
import com.apeun.gidaechi.message.model.room.MessageRoomModel
import com.apeun.gidaechi.common.model.Result
import kotlinx.coroutines.flow.Flow


interface MessageRepository {

    suspend fun sendMessage(chatRoomId: Int, message: String): Result<Boolean>

    suspend fun subscribeRoom(chatRoomId: Int): Flow<Result<MessageTypeModel>>

    suspend fun reSubscribeRoom(chatRoomId: Int): Flow<Result<MessageTypeModel>>

    suspend fun getMessage(chatRoomId: Int, page: Int, size: Int): Flow<Result<MessageLoadModel>>

    suspend fun loadRoomInfo(isPersonal: Boolean, roomId: Int): Flow<Result<MessageRoomModel>>
}