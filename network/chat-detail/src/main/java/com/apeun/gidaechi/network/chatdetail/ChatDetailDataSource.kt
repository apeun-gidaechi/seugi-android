package com.apeun.gidaechi.network.chatdetail

import com.apeun.gidaechi.network.chatdetail.response.ChatDetailMessageResponse
import kotlinx.coroutines.flow.Flow

interface ChatDetailDataSource {
    suspend fun subscribeRoom(chatRoomId: Int): Flow<ChatDetailMessageResponse>

    suspend fun sendMessage(chatRoomId: Int, message: String): Boolean

    suspend fun connectStomp(accessToken: String)

    suspend fun reConnectStomp(accessToken: String)

    suspend fun getIsConnect(): Boolean
}