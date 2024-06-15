package com.apeun.gidaechi.data.personalchat.datasource

import android.util.Log
import com.apeun.gidaechi.data.personalchat.PersonalChatDataSource
import com.apeun.gidaechi.data.personalchat.response.PersonalChatRoomResponse
import com.apeun.gidaechi.network.core.SeugiUrl
import com.apeun.gidaechi.network.core.Test
import com.apeun.gidaechi.network.core.response.BaseResponse
import com.apeun.gidaechi.network.core.utiles.addTestHeader
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class PersonalChatDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient
): PersonalChatDataSource {
    override suspend fun getAllChat(workspaceId: String): BaseResponse<List<PersonalChatRoomResponse>> =
        httpClient.get("${SeugiUrl.PersonalChat.LOAD_ALL}/${workspaceId}") {
            addTestHeader(Test.TEST_TOKEN)
        }.body<BaseResponse<List<PersonalChatRoomResponse>>>()
}