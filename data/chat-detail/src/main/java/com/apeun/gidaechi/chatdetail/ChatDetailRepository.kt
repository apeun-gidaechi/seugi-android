package com.apeun.gidaechi.chatdetail

import com.apeun.gidaechi.chatdetail.model.ChatDetailTypeModel
import com.apeun.gidaechi.chatdetail.model.message.ChatDetailMessageLoadModel
import com.apeun.gidaechi.chatdetail.model.room.ChatRoomModel
import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.network.chatdetail.response.room.ChatDetailChatRoomResponse
import com.apeun.gidaechi.network.core.response.BaseResponse
import kotlinx.coroutines.flow.Flow


interface ChatDetailRepository {

    suspend fun sendMessage(chatRoomId: Int, message: String): Result<Boolean>

    suspend fun subscribeRoom(chatRoomId: Int): Flow<Result<ChatDetailTypeModel>>

    suspend fun reSubscribeRoom(chatRoomId: Int): Flow<Result<ChatDetailTypeModel>>

    suspend fun getMessage(chatRoomId: Int, page: Int, size: Int): Flow<Result<ChatDetailMessageLoadModel>>

    suspend fun loadRoomInfo(isPersonal: Boolean, roomId: Int): Flow<Result<ChatRoomModel>>
}