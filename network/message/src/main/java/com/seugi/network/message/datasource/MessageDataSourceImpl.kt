package com.seugi.network.message.datasource

import android.util.Log
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.utiles.toJsonString
import com.seugi.network.core.utiles.toResponse
import com.seugi.network.message.MessageDataSource
import com.seugi.network.message.request.MessageRequest
import com.seugi.network.message.response.MessageRoomEventResponse
import com.seugi.network.message.response.message.MessageLoadResponse
import com.seugi.network.message.response.stomp.MessageStompLifecycleResponse
import com.seugi.stompclient.StompClient
import com.seugi.stompclient.dto.LifecycleEvent
import com.seugi.stompclient.dto.StompHeader
import com.seugi.stompclient.dto.StompMessage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.reactive.asFlow

class MessageDataSourceImpl @Inject constructor(
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
    private val stompClient: StompClient,
    private val httpClient: HttpClient,
) : MessageDataSource {

    override suspend fun connectStomp(accessToken: String) {
        val header = listOf(StompHeader("Authorization", accessToken))
        stompClient.connect(header)
    }

    override suspend fun reConnectStomp(accessToken: String, refreshToken: String): Unit = coroutineScope {
        stompClient.disconnectCompletable().subscribe { }
        connectStomp(accessToken)
    }

    override suspend fun getIsConnect(): Boolean = stompClient.isConnected

    override suspend fun getMessage(chatRoomId: String, page: Int, size: Int): BaseResponse<MessageLoadResponse> =
        httpClient.get("${SeugiUrl.Message.GET_MESSAGE}/$chatRoomId?page=$page&size=$size").body<BaseResponse<MessageLoadResponse>>()

    override suspend fun collectStompLifecycle(): Flow<MessageStompLifecycleResponse> = flow {
        stompClient.lifecycle().asFlow().collect {
            when (it.type) {
                LifecycleEvent.Type.OPENED -> {
                    emit(MessageStompLifecycleResponse.Open)
                }
                LifecycleEvent.Type.ERROR -> {
                    Log.e("TAG", "error", it.exception)
                    emit(MessageStompLifecycleResponse.Error(it.exception))
                }
                LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> {
                    emit(MessageStompLifecycleResponse.FailedServerHeartbeat)
                }
                LifecycleEvent.Type.CLOSED -> {
                    emit(MessageStompLifecycleResponse.Closed)
                }
                LifecycleEvent.Type.CONNECTED -> {
                    emit(MessageStompLifecycleResponse.Connected)
                }
            }
        }
    }

    override suspend fun subscribeRoom(chatRoomId: String): Flow<MessageRoomEventResponse> = flow {
        stompClient.topic(SeugiUrl.Message.SUBSCRIPTION + chatRoomId)
            .asFlow()
            .flowOn(dispatcher)
            .catch {
                it.printStackTrace()
            }
            .collect { message ->
                val type = message.payload.toResponse(MessageRoomEventResponse.Raw::class.java).type

                when (type) {
                    "MESSAGE", "FILE", "IMG", "ENTER", "LEFT" -> {
                        emit(message.payload.toResponse(MessageRoomEventResponse.MessageParent.Message::class.java))
                    }
                    "SUB" -> {
                        emit(message.payload.toResponse(MessageRoomEventResponse.Sub::class.java))
                    }
                    "DELETE_MESSAGE" -> {
                        emit(message.payload.toResponse(MessageRoomEventResponse.DeleteMessage::class.java))
                    }
                    "ADD_EMOJI" -> {
                        emit(message.payload.toResponse(MessageRoomEventResponse.AddEmoji::class.java))
                    }
                    "REMOVE_EMOJI" -> {
                        emit(message.payload.toResponse(MessageRoomEventResponse.RemoveEmoji::class.java))
                    }
                    "TRANSFER_ADMIN" -> {
                        emit(message.payload.toResponse(MessageRoomEventResponse.TransperAdmin::class.java))
                    }
                }
            }
    }

    override suspend fun sendMessage(chatRoomId: String, message: String, messageUUID: String, type: String): Boolean {
        // 연결이 되지 않는 경우 연결 강제성 부여
        if (!stompClient.isConnected) {
            return false
        }

        val body = MessageRequest(
            roomId = chatRoomId,
            message = message,
            uuid = messageUUID,
            type = type,
        ).toJsonString()

        val sendContent = StompMessage(
            "SEND",
            listOf(StompHeader(StompHeader.DESTINATION, SeugiUrl.Message.SEND)),
            body,
        )
        stompClient.send(sendContent).subscribe(
            {},
            {
                it.printStackTrace()
            },
        )
        return true
    }
}
