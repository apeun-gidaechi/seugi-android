package com.seugi.chatdatail.model

import com.seugi.data.core.model.ProfileModel
import com.seugi.data.core.model.UserInfoModel
import com.seugi.data.core.model.UserModel
import com.seugi.data.message.model.MessageRoomEvent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

data class ChatDetailUiState(
    val roomInfo: ChatRoomState? = null,
    val userInfo: UserModel? = null,
    val nowPage: Int = 0,
    val isInit: Boolean = false,
    val isLastPage: Boolean = false,
    val message: ImmutableList<MessageRoomEvent.MessageParent> = persistentListOf(),
    val users: ImmutableMap<Long, UserModel> = persistentMapOf(),
    val workspaceUsers: ImmutableList<ProfileModel> = persistentListOf(),
    val workspaceUsersMap: ImmutableMap<Long, ProfileModel> = persistentMapOf(),
)

data class ChatRoomState(
    val id: String,
    val roomName: String,
    val members: ImmutableList<UserInfoModel> = persistentListOf(),
    val adminId: Long,
)

sealed interface ChatDetailSideEffect {
    data object SuccessLeft : ChatDetailSideEffect
    data class FailedLeft(val throwable: Throwable) : ChatDetailSideEffect

    data class SuccessMoveRoom(val chatRoomId: String) : ChatDetailSideEffect
    data class FailedMoveRoom(val throwable: Throwable) : ChatDetailSideEffect
}
