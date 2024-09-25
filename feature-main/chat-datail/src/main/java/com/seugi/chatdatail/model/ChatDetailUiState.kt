package com.seugi.chatdatail.model

import com.seugi.data.message.model.MessageEmojiModel
import com.seugi.data.message.model.MessageLifeType
import com.seugi.data.message.model.MessageRoomEvent
import com.seugi.data.message.model.MessageUserModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import java.time.LocalDateTime

data class ChatDetailUiState(
    val roomInfo: ChatRoomState? = null,
    val userInfo: MessageUserModel? = null,
    val nowPage: Int = 0,
    val isInit: Boolean = false,
    val isLastPage: Boolean = false,
    val message: ImmutableList<MessageRoomEvent.MessageParent> = persistentListOf(),
    val users: ImmutableMap<Int, MessageUserModel> = persistentMapOf(),
)

data class ChatRoomState(
    val id: String,
    val roomName: String,
    val members: ImmutableList<MessageUserModel> = persistentListOf(),
)

sealed interface ChatDetailSideEffect {
    data object SuccessLeft : ChatDetailSideEffect
    data class FailedLeft(val throwable: Throwable) : ChatDetailSideEffect
}
