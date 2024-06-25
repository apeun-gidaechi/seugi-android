package com.apeun.gidaechi.network.groupchat.datasource

import com.apeun.gidaechi.data.personalchat.request.GroupChatCreateRequest
import com.apeun.gidaechi.network.core.SeugiUrl
import com.apeun.gidaechi.network.core.Test
import com.apeun.gidaechi.network.core.response.BaseResponse
import com.apeun.gidaechi.network.core.response.ChatRoomResponse
import com.apeun.gidaechi.network.core.utiles.addTestHeader
import com.apeun.gidaechi.network.groupchat.GroupChatDataSource
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
            addTestHeader(Test.TEST_TOKEN)
        }.body()

    override suspend fun createChat(
        workspaceId: String,
        roomName: String,
        joinUsers: List<Int>,
        chatRoomImg: String,
    ): BaseResponse<String> =
        httpClient.post(SeugiUrl.GroupChat.CREATE) {
            addTestHeader(Test.TEST_TOKEN)
            setBody(
                GroupChatCreateRequest(
                    workspaceId = workspaceId,
                    roomName = roomName,
                    joinUsers = joinUsers,
                    chatRoomImg = chatRoomImg
                )
            )
        }.body()
}
