package com.seugi.network.groupchat.datasource

import com.seugi.data.personalchat.request.GroupChatCreateRequest
import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.ChatRoomResponse
import com.seugi.network.groupchat.GroupChatDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class GroupChatDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : GroupChatDataSource {
    override suspend fun getGroupRoomList(workspaceId: String): BaseResponse<List<ChatRoomResponse>> =
        httpClient.get("${SeugiUrl.GroupChat.LOAD_ALL}/$workspaceId") {
        }.body()

    override suspend fun createChat(workspaceId: String, roomName: String, joinUsers: List<Int>, chatRoomImg: String): BaseResponse<String> =
        httpClient.post(SeugiUrl.GroupChat.CREATE) {
            setBody(
                GroupChatCreateRequest(
                    workspaceId = workspaceId,
                    roomName = roomName,
                    joinUsers = joinUsers,
                    chatRoomImg = chatRoomImg,
                ),
            )
        }.body()
}
