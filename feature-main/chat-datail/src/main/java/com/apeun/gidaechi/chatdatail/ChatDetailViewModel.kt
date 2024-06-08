package com.apeun.gidaechi.chatdatail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apeun.gidaechi.chatdatail.mapper.toState
import com.apeun.gidaechi.chatdatail.model.ChatDetailChatTypeState
import com.apeun.gidaechi.chatdatail.model.ChatDetailMessageState
import com.apeun.gidaechi.chatdatail.model.ChatDetailUiState
import com.apeun.gidaechi.chatdatail.model.ChatRoomState
import com.apeun.gidaechi.message.MessageRepository
import com.apeun.gidaechi.message.model.isMessage
import com.apeun.gidaechi.message.model.message.MessageLoadModel
import com.apeun.gidaechi.message.model.message.MessageMessageModel
import com.apeun.gidaechi.message.model.message.MessageUserModel
import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.data.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Duration

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ChatDetailUiState())
    val state = _state.asStateFlow()

    private var subscribeChat: Job? = null

    fun loadInfo(
        chatRoomId: String,
        isPersonal: Boolean,
        workspaceId: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        _state.value = _state.value.copy(
            roomInfo = ChatRoomState(
                chatRoomId,
                "",
                members = persistentListOf(),
            ),
//            userInfo = ChatDetailMessageUserModel(
//                2,
//                "박병준",
//                null
//            ),
        )
        initProfile(workspaceId)
        initPage()
        loadRoom(chatRoomId, isPersonal)
    }

    private fun initProfile(workspaceId: String) = viewModelScope.launch {
        profileRepository.getProfile(workspaceId).collect {
            when (it) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        userInfo = MessageUserModel(
                            id = 2,
                            name = it.data.nick,
                            profile = null,
                        )
                    )
                    Log.d("TAG", "initProfile: ${_state.value.userInfo?.id}")
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    it.throwable.printStackTrace()
                }
            }
        }
    }

    private fun loadRoom(
        chatRoomId: String,
        isPersonal: Boolean
    ) = viewModelScope.launch {
        messageRepository.loadRoomInfo(
            isPersonal = isPersonal,
            roomId = chatRoomId
        ).collect {
            when (it) {
                is Result.Success -> {
                    val users: MutableMap<Int, MessageUserModel> = mutableMapOf()
                    it.data.memberList.forEach { user ->
                        users[user.id] = user
                    }
                    _state.value = _state.value.copy(
                        roomInfo = ChatRoomState(
                            id = chatRoomId,
                            roomName = it.data.chatName,
                            members = it.data.memberList.toImmutableList(),
                        ),
                        users = users.toImmutableMap()
                    )
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    it.throwable.printStackTrace()
                }
            }
        }
    }

    fun channelSend(
        content: String
    ) {
        viewModelScope.launch {
            val e = messageRepository.sendMessage("665d9ec15e65717b19a62701", content)
            Log.d("TAG", "testSend: $e")
        }
    }

    fun channelReconnect(

    ) {
        viewModelScope.launch {
            subscribeChat?.cancel()
            subscribeChat = viewModelScope.async {
                messageRepository.reSubscribeRoom(
                    chatRoomId = "665d9ec15e65717b19a62701"
                ).collect {
                    when (it) {
                        is Result.Success -> {
                            val dataType = it.data.type
                            if (dataType.isMessage()) {
                                val data = it.data as MessageMessageModel
                                val message = _state.value.message.toMutableList()
                                val formerItem = _state.value.message.getOrNull(_state.value.message.lastIndex)

                                val isFirst = data.author != formerItem?.author?.id
                                val isMe = data.author == _state.value.userInfo?.id

                                if (formerItem?.isLast == true && formerItem.author.id == data.author) {
                                    message.add(
                                        message.removeLast().copy(
                                            isLast = false
                                        )
                                    )
                                }
                                message.add(
                                    data.toState(
                                        isFirst = isFirst,
                                        isLast = true,
                                        isMe = isMe,
                                        author = _state.value.users.getOrDefault(data.author, MessageUserModel(data.author))
                                    )
                                )
                                _state.value = _state.value.copy(
                                    message = message.toImmutableList()
                                )
                            }
                        }
                        is Result.Loading -> {}
                        is Result.Error -> {

                        }
                    }
                }
            }
            (subscribeChat as Deferred<*>).await()
            Log.d("TAG", "testReconnect: ")
            channelReconnect()
        }
    }

    fun subscribeCancel() {
        subscribeChat?.cancel()
        subscribeChat = null
    }

    fun nextPage() {
        with(state.value) {
            if (!isInit || isLastPage) { return }
            _state.value = _state.value.copy(
                nowPage = state.value.nowPage + 1
            )
            loadNowPage()
        }
    }

    fun prevPage() {
        with(state.value) {
            if (!isInit || nowPage == 0) { return }
            _state.value = _state.value.copy(
                nowPage = nowPage - 1
            )
            loadNowPage()
        }
    }

    private fun initPage() = viewModelScope.launch(Dispatchers.IO) {
        messageRepository.getMessage(state.value.roomInfo?.id?: "665d9ec15e65717b19a62701", state.value.nowPage, PAGE_SIZE).collect {
            it.collectMessage()
        }
    }

    private fun loadNowPage() {
        viewModelScope.launch(Dispatchers.IO) {
            messageRepository.getMessage(state.value.roomInfo?.id?: "665d9ec15e65717b19a62701", state.value.nowPage, PAGE_SIZE).collect {
                it.collectMessage()
            }
        }
    }

    private fun Result<MessageLoadModel>.collectMessage() {
        viewModelScope.launch(Dispatchers.IO) {
            when (this@collectMessage) {
                is Result.Success -> {
                    val chatData = _state.value.message.toMutableList()
                    val data = this@collectMessage.data.messages.reversed()
                    data.forEachIndexed { index, item ->
                        val formerItem = data.getOrNull(index - 1)
                        val nextItem = data.getOrNull(index + 1)

                        val isMe = item.author == state.value.userInfo?.id
                        val isLast = item.author != nextItem?.author ||
                                (
                                        Duration.between(
                                            item.timestamp,
                                            nextItem.timestamp,
                                        ).seconds >= 86400 && item.author == nextItem.author
                                        )

                        var isFirst = formerItem == null || item.author != formerItem.author
                        if (formerItem != null && Duration.between(
                                formerItem.timestamp,
                                item.timestamp,
                            ).seconds >= 86400
                        ) {
                            isFirst = true
                            chatData.add(
                                ChatDetailMessageState(
                                    chatRoomId = item.chatRoomId,
                                    type = ChatDetailChatTypeState.DATE,
                                    timestamp = item.timestamp
                                )
                            )
                        }
//                        if (item.type == ChatType.ENTER) {
//                            formerItem?.let {
//                                Log.d("TAG", "collectMessage: ${it.message}")
//                            }
//                            Log.d("TAG", "collectMessage: $formerItem $nextItem")
//                            Log.d("TAG", "collectMessage: $item")
//                        }
                        chatData.add(
                            item.toState(
                                isFirst = isFirst,
                                isLast = true,
                                isMe = isMe,
                                author = _state.value.users.getOrDefault(item.author, MessageUserModel(item.author))
                            )
                        )
                    }
                    var isLast = state.value.isLastPage
                    if (data.size < PAGE_SIZE) {
                        isLast = true
                    }
                    _state.value = _state.value.copy(
                        message = chatData.sortedBy {
                            it.timestamp
                        }.toImmutableList(),
                        isInit = true,
                        isLastPage = isLast
                    )
                }

                is Result.Loading -> {}
                is Result.Error -> {}
            }
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
