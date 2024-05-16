package com.apeun.gidaechi.network.chatdetail.datasource

import android.util.Log
import com.apeun.gidaechi.common.utiles.DispatcherType
import com.apeun.gidaechi.common.utiles.SeugiDispatcher
import com.apeun.gidaechi.network.chatdetail.ChatDetailDataSource
import com.apeun.gidaechi.network.chatdetail.request.ChatDetailMessageRequest
import com.apeun.gidaechi.network.chatdetail.response.ChatDetailTypeResponse
import com.apeun.gidaechi.network.chatdetail.response.message.ChatDetailMessageDeleteResponse
import com.apeun.gidaechi.network.chatdetail.response.message.ChatDetailMessageEmojiResponse
import com.apeun.gidaechi.network.chatdetail.response.message.ChatDetailMessageLoadResponse
import com.apeun.gidaechi.network.chatdetail.response.message.ChatDetailMessageResponse
import com.apeun.gidaechi.network.chatdetail.response.sub.ChatDetailSubResponse
import com.apeun.gidaechi.network.core.SeugiUrl
import com.apeun.gidaechi.network.core.Test
import com.apeun.gidaechi.network.core.response.BaseResponse
import com.apeun.gidaechi.network.core.utiles.addTestHeader
import com.apeun.gidaechi.network.core.utiles.toJsonString
import com.apeun.gidaechi.network.core.utiles.toResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
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
    private val stompClient: StompClient,
    private val httpClient: HttpClient
): ChatDetailDataSource {

    override suspend fun connectStomp(accessToken: String) {
        val header = listOf(StompHeader("Authorization", accessToken))
        stompClient.connect(header)
    }

    override suspend fun reConnectStomp(accessToken: String) {
        stompClient.disconnectCompletable().subscribe() {
            Log.d("TAG", "reConnectStomp: 시작")
        }
        this.connectStomp(accessToken)
        Log.d("TAG", "reConnectStomp: 끛")
    }

    override suspend fun getIsConnect(): Boolean = stompClient.isConnected

    override suspend fun getMessage(chatRoomId: Int, page: Int): BaseResponse<ChatDetailMessageLoadResponse> =
        httpClient.get("${SeugiUrl.Chat.GET_MESSAGE}/${chatRoomId}?page=${page}") {
            addTestHeader(Test.TEST_TOKEN)
        }.body<BaseResponse<ChatDetailMessageLoadResponse>>()

    override suspend fun subscribeRoom(
        chatRoomId: Int,
    ): Flow<ChatDetailTypeResponse> = flow {

        stompClient.topic(SeugiUrl.Chat.SUBSCRIPTION + chatRoomId)
            .asFlow()
            .flowOn(dispatcher)
            .catch {
                it.printStackTrace()
            }
            .collect { message ->
                val type = message.payload.toResponse(ChatDetailMessageResponse::class.java).type
                when (type) {
                    "MESSAGE", "FILE", "IMG", "ENTER", "LEFT" -> {
                        emit(message.payload.toResponse(ChatDetailMessageResponse::class.java))
                    }
                    "SUB" -> {
                        emit(message.payload.toResponse(ChatDetailSubResponse::class.java))
                    }
                    "DELETE_MESSAGE" -> {
                        emit(message.payload.toResponse(ChatDetailMessageDeleteResponse::class.java))
                    }
                    "ADD_EMOJI", "REMOVE_EMOJI" -> {
                        emit(message.payload.toResponse(ChatDetailMessageEmojiResponse::class.java))
                    }
                    else -> {}
                }
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
