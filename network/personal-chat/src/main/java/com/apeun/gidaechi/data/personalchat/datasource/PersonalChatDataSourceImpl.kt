package com.seugi.data.personalchat.datasource

import com.seugi.data.personalchat.PersonalChatDataSource
import com.seugi.data.personalchat.request.PersonalChatCreateRequest
import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.ChatRoomResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class PersonalChatDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : PersonalChatDataSource {
    override suspend fun getAllChat(workspaceId: String): BaseResponse<List<ChatRoomResponse>> = httpClient.get(SeugiUrl.PersonalChat.LOAD_ALL) {
        parameter("workspace", workspaceId)

    }.body()

    override suspend fun createChat(workspaceId: String, roomName: String, joinUsers: List<Int>, chatRoomImg: String): BaseResponse<String> = httpClient.post(SeugiUrl.PersonalChat.CREATE) {
        setBody(
            PersonalChatCreateRequest(
                workspaceId = workspaceId,
                roomName = roomName,
                joinUsers = joinUsers,
                chatRoomImg = chatRoomImg,
            ),
        )
    }.body()
}
