package com.seugi.network.message.datasource

import android.util.Log
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.Test
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.utiles.addTestHeader
import com.seugi.network.core.utiles.toJsonString
import com.seugi.network.core.utiles.toResponse
import com.seugi.network.message.MessageDataSource
import com.seugi.network.message.request.MessageRequest
import com.seugi.network.message.response.MessageTypeResponse
import com.seugi.network.message.response.message.MessageDeleteResponse
import com.seugi.network.message.response.message.MessageEmojiResponse
import com.seugi.network.message.response.message.MessageLoadResponse
import com.seugi.network.message.response.message.MessageMessageResponse
import com.seugi.network.message.response.room.MessageRoomMemberResponse
import com.seugi.network.message.response.room.MessageRoomResponse
import com.seugi.network.message.response.sub.MessageSubResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.reactive.asFlow
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.StompHeader
import ua.naiksoftware.stomp.dto.StompMessage

class MessageDataSourceImpl @Inject constructor(
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
    private val stompClient: StompClient,
    private val httpClient: HttpClient,
) : MessageDataSource {

    override suspend fun connectStomp(accessToken: String) {
        val header = listOf(StompHeader("Authorization", accessToken))
        stompClient.connect(header)
    }

    override suspend fun reConnectStomp(accessToken: String) {
        stompClient.disconnectCompletable().subscribe {
            Log.d("TAG", "reConnectStomp: 시작")
        }
        this.connectStomp(accessToken)
        Log.d("TAG", "reConnectStomp: 끛")
    }

    override suspend fun getIsConnect(): Boolean = stompClient.isConnected

    override suspend fun getMessage(chatRoomId: String, page: Int, size: Int): BaseResponse<MessageLoadResponse> =
        httpClient.get("${SeugiUrl.Message.GET_MESSAGE}/$chatRoomId?page=$page&size=$size").body<BaseResponse<MessageLoadResponse>>()

    override suspend fun loadRoomInfo(isPersonal: Boolean, roomId: String): BaseResponse<MessageRoomResponse> {
        val url = "${SeugiUrl.Chat.ROOT}/${if (isPersonal) "personal" else "group"}/search/room/$roomId"
        return httpClient.get(url).body()
    }

    override suspend fun loadRoomMember(roomId: String): BaseResponse<MessageRoomMemberResponse> = httpClient.get("${SeugiUrl.Chat.LOAD_MEMBER}/$roomId").body()

    override suspend fun leftRoom(chatRoomId: String): BaseResponse<Unit?> = httpClient.patch("${SeugiUrl.Chat.LEFT}/$chatRoomId").body()

    override suspend fun testGetToken(): String = Test.TEST_TOKEN

    override suspend fun subscribeRoom(chatRoomId: String): Flow<MessageTypeResponse> = flow {
        stompClient.topic(SeugiUrl.Message.SUBSCRIPTION + chatRoomId)
            .asFlow()
            .flowOn(dispatcher)
            .collect { message ->
                val type = message.payload.toResponse(MessageMessageResponse::class.java).type
                when (type) {
                    "MESSAGE", "FILE", "IMG", "ENTER", "LEFT" -> {
                        emit(message.payload.toResponse(MessageMessageResponse::class.java))
                    }
                    "SUB" -> {
                        emit(message.payload.toResponse(MessageSubResponse::class.java))
                    }
                    "DELETE_MESSAGE" -> {
                        emit(message.payload.toResponse(MessageDeleteResponse::class.java))
                    }
                    "ADD_EMOJI", "REMOVE_EMOJI" -> {
                        emit(message.payload.toResponse(MessageEmojiResponse::class.java))
                    }
                    else -> {}
                }
            }
    }

    override suspend fun sendMessage(chatRoomId: String, message: String): Boolean {
        // 연결이 되지 않는 경우 연결 강제성 부여
        if (!stompClient.isConnected) {
            return false
        }

        val body = MessageRequest(
            roomId = chatRoomId,
            message = message,
        ).toJsonString()

        val sendContent = StompMessage("SEND", listOf(StompHeader(StompHeader.DESTINATION, "/pub/chat.message")), body)
        val result = stompClient.send(sendContent).subscribe {}
        return true
    }
}
