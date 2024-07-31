package com.seugi.chatdatail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.chatdatail.mapper.toState
import com.seugi.chatdatail.model.ChatDetailChatTypeState
import com.seugi.chatdatail.model.ChatDetailMessageState
import com.seugi.chatdatail.model.ChatDetailSideEffect
import com.seugi.chatdatail.model.ChatDetailUiState
import com.seugi.chatdatail.model.ChatRoomState
import com.seugi.common.model.Result
import com.seugi.data.message.MessageRepository
import com.seugi.data.message.model.isMessage
import com.seugi.data.message.model.isSub
import com.seugi.data.message.model.message.MessageLoadModel
import com.seugi.data.message.model.message.MessageMessageModel
import com.seugi.data.message.model.message.MessageUserModel
import com.seugi.data.message.model.sub.MessageSubModel
import com.seugi.data.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Duration
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class BackupChatDetailViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val profileRepository: ProfileRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ChatDetailUiState())
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<ChatDetailSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private var subscribeChat: Job? = null

    fun loadInfo(chatRoomId: String, isPersonal: Boolean, workspaceId: String) = viewModelScope.launch(Dispatchers.IO) {
        _state.value = _state.value.copy(
            roomInfo = ChatRoomState(
                chatRoomId,
                "",
                members = persistentListOf(),
            ),
        )
        initProfile(workspaceId)
        initPage()
        loadRoom(chatRoomId, isPersonal)
    }

    private fun initProfile(workspaceId: String) = viewModelScope.launch {
        profileRepository.getProfile(workspaceId).collect {
            when (it) {
                is Result.Success -> {
                    val state = _state.value
                    _state.value = _state.value.copy(
                        userInfo = MessageUserModel(
                            id = it.data.member.id,
                            name = it.data.nick,
                            profile = null,
                        ),
                    )
                    if (state.message.size > 0) {
                        val messageList = state.message.map { messageState ->
                            messageState.copy(
                                isMe = messageState.author.id == it.data.member.id,
                            )
                        }
                        _state.update {
                            it.copy(
                                message = messageList.toImmutableList(),
                            )
                        }
                    }
                    Log.d("TAG", "initProfile: ${_state.value.userInfo?.id}")
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    it.throwable.printStackTrace()
                }
            }
        }
    }

    private fun loadRoom(chatRoomId: String, isPersonal: Boolean) = viewModelScope.launch {
        messageRepository.loadRoomInfo(
            isPersonal = isPersonal,
            roomId = chatRoomId,
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
                        users = users.toImmutableMap(),
                    )
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    it.throwable.printStackTrace()
                }
            }
        }
    }

    fun channelSend(content: String) {
        viewModelScope.launch {
            val e = messageRepository.sendMessage(state.value.roomInfo?.id ?: "", content)
            Log.d("TAG", "testSend: $e")
        }
    }

    fun channelReconnect() {
        viewModelScope.launch {
            subscribeChat?.cancel()
            subscribeChat = viewModelScope.async {
                messageRepository.reSubscribeRoom(
                    chatRoomId = state.value.roomInfo?.id ?: "",
                ).collect {
                    when (it) {
                        is Result.Success -> {
                            val dataType = it.data.type
                            when {
                                dataType.isMessage() -> {
                                    val data = it.data as MessageMessageModel
                                    val message = _state.value.message.toMutableList()
                                    val formerItem = _state.value.message.getOrNull(_state.value.message.lastIndex)

                                    val isFirst = data.author != formerItem?.author?.id
                                    val isMe = data.author == _state.value.userInfo?.id

                                    if (formerItem?.isLast == true && formerItem.author.id == data.author) {
                                        message.add(
                                            message.removeLast().copy(
                                                isLast = false,
                                            ),
                                        )
                                    }
                                    message.add(
                                        data.toState(
                                            isFirst = isFirst,
                                            isLast = true,
                                            isMe = isMe,
                                            author = _state.value.users.getOrDefault(data.author, MessageUserModel(data.author)),
                                        ),
                                    )
                                    _state.value = _state.value.copy(
                                        message = message.toImmutableList(),
                                    )
                                }
                                dataType.isSub() -> {
                                    val messageSubModel = it.data as MessageSubModel
                                    _state.update {
                                        it.copy(
                                            message = it.message.map { nowMessage ->
                                                nowMessage.copy(
                                                    read = nowMessage.read
                                                        .toMutableList()
                                                        .apply {
                                                            add(messageSubModel.userId)
                                                        }
                                                        .distinct()
                                                        .toImmutableList(),
                                                )
                                            }.toImmutableList(),
                                        )
                                    }
                                }
                            }
                        }
                        is Result.Loading -> {}
                        is Result.Error -> {
                            it.throwable.printStackTrace()
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

    fun leftRoom(chatRoomId: String) {
        viewModelScope.launch {
            messageRepository.leftRoom(chatRoomId).collect {
                when (it) {
                    is Result.Success -> {
                        Log.d("TAG", "leftRoom: sucesss")
                        _sideEffect.send(ChatDetailSideEffect.SuccessLeft)
                    }
                    is Result.Error -> {
                        Log.d("TAG", "leftRoom: failed")
                        it.throwable.printStackTrace()
                        _sideEffect.send(ChatDetailSideEffect.FailedLeft(it.throwable))
                    }
                    is Result.Loading -> {}
                }
            }
        }
    }

    fun nextPage() {
        with(state.value) {
            if (!isInit || isLastPage) {
                return
            }
            _state.value = _state.value.copy(
                nowPage = state.value.nowPage + 1,
            )
            loadNowPage()
        }
    }

    fun prevPage() {
        with(state.value) {
            if (!isInit || nowPage == 0) {
                return
            }
            _state.value = _state.value.copy(
                nowPage = nowPage - 1,
            )
            loadNowPage()
        }
    }

    private fun initPage() = viewModelScope.launch(Dispatchers.IO) {
        messageRepository.getMessage(state.value.roomInfo?.id ?: "665d9ec15e65717b19a62701", state.value.nowPage, PAGE_SIZE).collect {
            it.collectMessage()
        }
    }

    private fun loadNowPage() {
        viewModelScope.launch(Dispatchers.IO) {
            messageRepository.getMessage(state.value.roomInfo?.id ?: "665d9ec15e65717b19a62701", state.value.nowPage, PAGE_SIZE).collect {
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
                                    timestamp = item.timestamp,
                                ),
                            )
                        }
                        chatData.add(
                            item.toState(
                                isFirst = isFirst,
                                isLast = isLast,
                                isMe = isMe,
                                author = _state.value.users.getOrDefault(item.author, MessageUserModel(item.author)),
                            ),
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
                        isLastPage = isLast,
                    )
                }

                is Result.Loading -> {}
                is Result.Error -> {
                    this@collectMessage.throwable.printStackTrace()
                }
            }
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
