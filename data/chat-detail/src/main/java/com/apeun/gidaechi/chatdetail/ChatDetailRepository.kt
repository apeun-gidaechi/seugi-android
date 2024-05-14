package com.apeun.gidaechi.chatdetail

import com.apeun.gidaechi.chatdetail.model.ChatDetailMessageModel
import com.apeun.gidaechi.common.model.Result
import kotlinx.coroutines.flow.Flow


interface ChatDetailRepository {

    suspend fun sendMessage(chatRoomId: Int, message: String): Result<Boolean>

    suspend fun subscribeRoom(chatRoomId: Int): Flow<Result<ChatDetailMessageModel>>
}