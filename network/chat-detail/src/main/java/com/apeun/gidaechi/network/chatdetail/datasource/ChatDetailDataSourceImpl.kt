package com.apeun.gidaechi.network.chatdetail.datasource

import com.apeun.gidaechi.common.utiles.DispatcherType
import com.apeun.gidaechi.common.utiles.SeugiDispatcher
import com.apeun.gidaechi.network.chatdetail.ChatDetailDataSource
import com.apeun.gidaechi.network.chatdetail.request.ChatDetailMessageRequest
import com.apeun.gidaechi.network.chatdetail.response.ChatDetailMessageResponse
import com.apeun.gidaechi.network.core.SeugiUrl
import com.apeun.gidaechi.network.core.utiles.toJsonString
import com.apeun.gidaechi.network.core.utiles.toResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.reactive.asFlow
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.StompHeader
import ua.naiksoftware.stomp.dto.StompMessage
import javax.inject.Inject


class ChatDetailDataSourceImpl @Inject constructor(
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
    private val stompClient: StompClient
): ChatDetailDataSource {

    override suspend fun connectStomp(accessToken: String) {
        val header = listOf(StompHeader("Authorization", accessToken))
        var test = false
        stompClient.connect(header)
    }

    override suspend fun getIsConnect(): Boolean = stompClient.isConnected

    override suspend fun subscribeRoom(
        chatRoomId: Int,
    ): Flow<ChatDetailMessageResponse> = flow {

        stompClient.topic(SeugiUrl.Chat.SUBSCRIPTION + chatRoomId)
            .asFlow()
            .flowOn(dispatcher)
            .catch {
                it.printStackTrace()
            }
            .collect { message ->
                emit(message.payload.toResponse(ChatDetailMessageResponse::class.java))
            }

    }

    override suspend fun sendMessage(chatRoomId: Int, message: String): Boolean {
        // 연결이 되지 않는 경우 연결 강제성 부여
        if (!stompClient.isConnected) {
            return false
        }

        val body = ChatDetailMessageRequest(
            roomId = chatRoomId,
            message = message
        ).toJsonString()

        val sendContent = StompMessage("SEND", listOf(StompHeader(StompHeader.DESTINATION, "/pub/chat.message")), body)
        stompClient.send(sendContent).subscribe() {}
        return true
    }


}
