package com.seugi.network.groupchat.datasource

import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.ChatRoomResponse
import com.seugi.network.groupchat.GroupChatDataSource
import com.seugi.network.personalchat.request.GroupChatCreateRequest
import com.seugi.network.personalchat.request.GroupChatMemberAddRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class GroupChatDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : GroupChatDataSource {
    override suspend fun getGroupRoomList(workspaceId: String): BaseResponse<List<ChatRoomResponse>> = httpClient.get(SeugiUrl.GroupChat.LOAD_ALL) {
        parameter("workspace", workspaceId)
    }.body()

    override suspend fun createChat(workspaceId: String, roomName: String, joinUsers: List<Long>, chatRoomImg: String): BaseResponse<String> = httpClient.post(SeugiUrl.GroupChat.CREATE) {
        setBody(
            GroupChatCreateRequest(
                workspaceId = workspaceId,
                roomName = roomName,
                joinUsers = joinUsers,
                chatRoomImg = chatRoomImg,
            ),
        )
    }.body()

    override suspend fun getChat(roomId: String): BaseResponse<ChatRoomResponse> = httpClient.get(SeugiUrl.GroupChat.SEARCH_ROOM + "/$roomId").body()

    override suspend fun leftRoom(chatRoomId: String): BaseResponse<Unit?> = httpClient.patch("${SeugiUrl.GroupChat.LEFT}/$chatRoomId").body()

    override suspend fun addMembers(
        chatRoomId: String,
        chatMemberUsers: List<Long>,
    ): BaseResponse<Unit?> = httpClient.post(SeugiUrl.GroupChat.MEMBER_ADD) {
        setBody(
            GroupChatMemberAddRequest(
                chatRoomId =  chatRoomId,
                chatMemberUsers = chatMemberUsers
            )
        )
    }.body()
}
