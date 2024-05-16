package com.apeun.gidaechi.network.chatdetail

import com.apeun.gidaechi.network.chatdetail.response.ChatDetailTypeResponse
import com.apeun.gidaechi.network.chatdetail.response.message.ChatDetailMessageLoadResponse
import com.apeun.gidaechi.network.core.response.BaseResponse
import kotlinx.coroutines.flow.Flow

interface ChatDetailDataSource {
    suspend fun subscribeRoom(chatRoomId: Int): Flow<ChatDetailTypeResponse>

    suspend fun sendMessage(chatRoomId: Int, message: String): Boolean

    suspend fun connectStomp(accessToken: String)

    suspend fun reConnectStomp(accessToken: String)

    suspend fun getIsConnect(): Boolean

    suspend fun getMessage(chatRoomId: Int, page: Int): BaseResponse<ChatDetailMessageLoadResponse>
}