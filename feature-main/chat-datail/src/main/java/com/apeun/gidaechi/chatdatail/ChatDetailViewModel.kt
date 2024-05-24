package com.apeun.gidaechi.chatdatail

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apeun.gidaechi.chatdatail.mapper.toState
import com.apeun.gidaechi.chatdatail.model.ChatDetailChatTypeState
import com.apeun.gidaechi.chatdatail.model.ChatDetailMessageState
import com.apeun.gidaechi.chatdatail.model.ChatDetailUiState
import com.apeun.gidaechi.chatdatail.model.ChatRoomState
import com.apeun.gidaechi.chatdetail.ChatDetailRepository
import com.apeun.gidaechi.chatdetail.model.ChatType
import com.apeun.gidaechi.chatdetail.model.isMessage
import com.apeun.gidaechi.chatdetail.model.message.ChatDetailMessageLoadModel
import com.apeun.gidaechi.chatdetail.model.message.ChatDetailMessageModel
import com.apeun.gidaechi.chatdetail.model.message.ChatDetailMessageUserModel
import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.data.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import java.time.Duration

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val repository: ChatDetailRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ChatDetailUiState())
    val state = _state.asStateFlow()

    private var subscribeChat: Job? = null

    fun loadInfo(
        chatRoomId: Int,
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
                        userInfo = ChatDetailMessageUserModel(
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
        chatRoomId: Int,
        isPersonal: Boolean
    ) = viewModelScope.launch {
        repository.loadRoomInfo(
            isPersonal = isPersonal,
            roomId = chatRoomId
        ).collect {
            when (it) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        roomInfo = ChatRoomState(
                            chatRoomId,
                            it.data.chatName,
                            members = it.data.memberList.map {
                                ChatDetailMessageUserModel(
                                    id = it,
                                    name = "test ${it}",
                                    profile = null
                                )
                            }.toImmutableList(),
                        ),
                    )
                }
                is Result.Loading -> {}
                is Result.Error -> {}
            }
        }
    }

    fun channelSend(
        content: String
    ) {
        viewModelScope.launch {
            val e = repository.sendMessage(99, content)
            Log.d("TAG", "testSend: $e")
        }
    }

    fun channelReconnect(

    ) {
        viewModelScope.launch {
            subscribeChat?.cancel()
            subscribeChat = viewModelScope.async {
                repository.reSubscribeRoom(
                    chatRoomId = 99
                ).collect {
                    when (it) {
                        is Result.Success -> {
                            val dataType = it.data.type
                            if (dataType.isMessage()) {
                                val data = it.data as ChatDetailMessageModel
                                val message = _state.value.message.toMutableList()
                                val formerItem = _state.value.message.getOrNull(_state.value.message.lastIndex)

                                val isFirst = data.author.id != formerItem?.author?.id
                                val isMe = data.author.id == _state.value.userInfo?.id

                                if (formerItem?.isLast == true && formerItem.author.id == data.author.id) {
                                    message.add(
                                        message.removeLast().copy(
                                            isLast = false
                                        )
                                    )
                                }
                                message.add(
                                    data.toState(isFirst, true, isMe)
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
        repository.getMessage(state.value.roomInfo?.id?: 0, state.value.nowPage, PAGE_SIZE).collect {
            it.collectMessage()
        }
    }

    private fun loadNowPage() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMessage(state.value.roomInfo?.id?: 0, state.value.nowPage, PAGE_SIZE).collect {
                it.collectMessage()
            }
        }
    }

    private fun Result<ChatDetailMessageLoadModel>.collectMessage() {
        viewModelScope.launch(Dispatchers.IO) {
            when (this@collectMessage) {
                is Result.Success -> {
                    val chatData = _state.value.message.toMutableList()
                    val data = this@collectMessage.data.messages.reversed()
                    data.forEachIndexed { index, item ->
                        val formerItem = data.getOrNull(index - 1)
                        val nextItem = data.getOrNull(index + 1)

                        val isMe = item.author.id == state.value.userInfo?.id
                        val isLast = item.author.id != nextItem?.author?.id ||
                                (
                                        Duration.between(
                                            item.timestamp,
                                            nextItem.timestamp,
                                        ).seconds >= 86400 && item.author.id == nextItem.author.id
                                        )

                        var isFirst = formerItem == null || item.author.id != formerItem.author.id
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
                        chatData.add(item.toState(isFirst, isLast, isMe))
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
