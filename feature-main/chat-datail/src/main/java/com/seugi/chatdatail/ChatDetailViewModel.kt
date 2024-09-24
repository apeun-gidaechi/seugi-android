package com.seugi.chatdatail

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.chatdatail.mapper.toState
import com.seugi.chatdatail.model.ChatDetailChatTypeState
import com.seugi.chatdatail.model.ChatDetailMessageState
import com.seugi.chatdatail.model.ChatDetailSideEffect
import com.seugi.chatdatail.model.ChatDetailUiState
import com.seugi.chatdatail.model.ChatLocalType
import com.seugi.chatdatail.model.ChatRoomState
import com.seugi.chatdatail.model.findBySendingMessage
import com.seugi.chatdatail.model.minus
import com.seugi.chatdatail.model.plus
import com.seugi.common.model.Result
import com.seugi.data.file.FileRepository
import com.seugi.data.file.model.FileType
import com.seugi.data.message.MessageRepository
import com.seugi.data.message.model.MessageType
import com.seugi.data.message.model.MessageTypeModel
import com.seugi.data.message.model.isMessage
import com.seugi.data.message.model.isSub
import com.seugi.data.message.model.message.MessageLoadModel
import com.seugi.data.message.model.message.MessageMessageModel
import com.seugi.data.message.model.message.MessageUserModel
import com.seugi.data.message.model.stomp.MessageStompLifecycleModel
import com.seugi.data.message.model.sub.MessageSubModel
import com.seugi.data.profile.ProfileRepository
import com.seugi.data.token.TokenRepository
import com.seugi.stompclient.StompException
import com.seugi.ui.toByteArray
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import java.net.SocketException
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.math.abs
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val profileRepository: ProfileRepository,
    private val tokenRepository: TokenRepository,
    private val fileRepository: FileRepository
) : ViewModel() {

    // 소켓 재연결로 인해 밀린 채팅을 저장
    private val _messageSaveQueueState: MutableStateFlow<ImmutableList<ChatLocalType>> = MutableStateFlow(persistentListOf())
    val messageSaveQueueState = _messageSaveQueueState.asStateFlow()

    private val _state = MutableStateFlow(ChatDetailUiState())
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<ChatDetailSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private var subscribeChat: Job? = null
    private var subscribeLifecycle: Job? = null

    fun loadInfo(userId: Int, chatRoomId: String, isPersonal: Boolean, workspaceId: String) = viewModelScope.launch(Dispatchers.IO) {
        _state.value = _state.value.copy(
            userInfo = MessageUserModel(userId),
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

    fun collectStompLifecycle() {
        viewModelScope.launch {
            val job = viewModelScope.async {
                messageRepository.collectStompLifecycle().collect {
                    Log.d("TAG", "collectStompLifecycle: ")
                    when (it) {
                        is Result.Success -> {
                            val data = it.data
                            Log.d("TAG", "collectStompLifecycle: $data")
                            when (data) {
                                is MessageStompLifecycleModel.Error -> {
                                    val exception = data.throwable as? Exception
                                    when (exception) {
                                        is StompException -> {
                                            tokenRepository.newToken().collect {
                                                when (it) {
                                                    is Result.Success -> {
                                                        messageRepository.getMessage(state.value.roomInfo?.id ?: "665d9ec15e65717b19a62701", 0, PAGE_SIZE).collect {
                                                            it.collectMessage()
                                                        }
                                                        channelReconnect()
                                                    }
                                                    else -> {}
                                                }
                                            }
                                        }
                                        is SocketException -> {
                                            channelReconnect()
                                        }
                                    }
                                }
                                else -> {}
                            }
                        }
                        is Result.Loading -> {}
                        is Result.Error -> {
                            it.throwable.printStackTrace()
                        }
                    }
                }
            }
            subscribeLifecycle = job
            job.await()
        }
    }

    fun channelSend(
        content: String,
        type: MessageType
    ) {
        viewModelScope.launch {
            val uuid = UUID.randomUUID().toString()
            _messageSaveQueueState.value += ChatLocalType.Send(content, uuid)
            val result = messageRepository.sendMessage(
                chatRoomId = state.value.roomInfo?.id ?: "",
                message = content,
                messageUUID = uuid,
                type = type
            )
            Log.d("TAG", "testSend: $result")
            when (result) {
                is Result.Success -> {
                    if (!result.data) {
                        _messageSaveQueueState.value -= ChatLocalType.Send(content, uuid)
                        _messageSaveQueueState.value += ChatLocalType.Failed(content, uuid)
                        channelReconnect()
                    }
                }
                is Result.Error -> {
                    result.throwable.printStackTrace()
                }
                is Result.Loading -> {}
            }
        }
    }

    fun channelSend(
        content: Bitmap,
        title: String,
    ) {
        viewModelScope.launch {
            fileRepository.fileUpload(
                type = FileType.IMG,
                fileName = title,
                byteArray = content.toByteArray()
            ).collect {
                when (it) {
                    is Result.Success -> {
                        channelSend(
                            content = it.data,
                            type = MessageType.IMG
                        )
                    }
                    Result.Loading -> {}
                    is Result.Error -> {}
                }
            }
        }
    }

    fun channelResend(content: String, uuid: String) {
        _messageSaveQueueState.value -= ChatLocalType.Failed(content, uuid)
        channelSend(content, MessageType.MESSAGE)
    }

    fun deleteFailedSend(content: String, uuid: String) {
        _messageSaveQueueState.value -= ChatLocalType.Failed(content, uuid)
    }

    fun channelReconnect() {
        viewModelScope.launch {
            subscribeChat?.cancel()
            val job = viewModelScope.async {
                messageRepository.reSubscribeRoom(
                    chatRoomId = state.value.roomInfo?.id ?: "",
                ).collect {
                    it.collectMessage()
                }
            }
            subscribeChat = job
            job.await()
            Log.d("TAG", "next Channel Connect: ")
            channelConnect()
            Log.d("TAG", "testReconnect: ")
        }
    }

    private fun channelConnect() {
        viewModelScope.launch {
            subscribeChat?.cancel()
            val job = viewModelScope.async {
                messageRepository.subscribeRoom(
                    chatRoomId = state.value.roomInfo?.id ?: "",
                ).collect {
                    it.collectMessage()
                }
            }
            subscribeChat = job
            job.await()
        }
    }

    fun subscribeCancel() {
        subscribeLifecycle?.cancel()
        subscribeLifecycle = null
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

    private fun Result<MessageTypeModel>.collectMessage() = when (this) {
        is Result.Success -> {
            val dataType = data.type
            when {
                dataType.isMessage() -> {
                    val data = data as MessageMessageModel
                    val message = _state.value.message.toMutableList()
                    val formerItem = _state.value.message.firstOrNull()

                    var isFirst = data.author != formerItem?.author?.id
                    val isMe = data.author == _state.value.userInfo?.id

                    if (
                        formerItem?.isLast == true && formerItem.author.id == data.author &&
                        !formerItem.timestamp.isDifferentMin(data.timestamp)
                    ) {
                        message.add(
                            index = 0,
                            element = message.removeFirst().copy(
                                isLast = false,
                            ),
                        )
                    }

                    if (formerItem != null && data.timestamp.isDifferentDay(formerItem.timestamp)
                    ) {
                        isFirst = true
                        message.add(
                            index = 0,
                            element = ChatDetailMessageState(
                                chatRoomId = data.chatRoomId,
                                type = ChatDetailChatTypeState.DATE,
                                timestamp = LocalDateTime.of(data.timestamp.year, data.timestamp.monthValue, data.timestamp.dayOfMonth, 0, 0),
                            ),
                        )
                    }

                    message.add(
                        index = 0,
                        element = data.toState(
                            isFirst = isFirst,
                            isLast = true,
                            isMe = isMe,
                            author = _state.value.users.getOrDefault(data.author, MessageUserModel(data.author)),
                        ),
                    )

                    // messageQueue 삭제 로직
                    if (data.author == _state.value.userInfo?.id) {
                        val value = _messageSaveQueueState.value.findBySendingMessage(data.message)
                        Log.d("TAG", "collectMessage: $value")
                        if (value != null) {
                            _messageSaveQueueState.value -= value
                            Log.d("TAG", "collectMessage: ${_messageSaveQueueState.value}")
                        }
                    }
                    _state.update {
                        it.copy(
                            message = message.toImmutableList(),
                        )
                    }
                }
                dataType.isSub() -> {
                    val messageSubModel = data as MessageSubModel
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

                else -> {}
            }
        }
        is Result.Loading -> {}
        is Result.Error -> {
            throwable.printStackTrace()
        }
    }

    private fun Result<MessageLoadModel>.collectMessage() = viewModelScope.launch(Dispatchers.IO) {
        when (this@collectMessage) {
            is Result.Success -> {
                val chatData = _state.value.message.toMutableList()
                // 기존 채팅과, 새로운 채팅간 중복 문제 해결 로직
                val filterList = chatData.map { it.id }
                val data = this@collectMessage.data.messages.filter { it.id !in filterList }

                // 기존 채팅의 마지막 isFirst 변경
                // 채팅 재 연결시 페이지 0 을 불러오기에, 기존 데이터와 시간 비교
                if (
                    data.firstOrNull() != null &&
                    data.first().author == chatData.lastOrNull()?.author?.id &&
                    data.first().timestamp > chatData.last().timestamp
                ) {
                    val changeData = chatData.removeLast()
                    chatData.add(chatData.lastIndex, changeData.copy(isFirst = false))
                }
                data.forEachIndexed { index, item ->
                    val formerItem = data.getOrNull(index + 1)
                    val nextItem = data.getOrNull(index - 1)

                    val isMe = item.author == state.value.userInfo?.id
                    var isFirst = formerItem == null || item.author != formerItem.author

                    // 새로 불러온 채팅과, 기존 마지막 채팅과 동기화
                    // 채팅 재 연결시 페이지 0 을 불러오기에, 기존 데이터와 시간 비교
                    val isLast = if (chatData.isEmpty() || (index != 0 && item.timestamp > chatData.lastOrNull()?.timestamp)) {
                        // 기존 로직
                        item.author != nextItem?.author ||
                            (
                                item.author == formerItem?.author && item.timestamp.isDifferentMin(
                                    nextItem.timestamp,
                                )
                                )
                    } else {
                        // 동기화 로직
                        val chatDataNextItem = chatData.last()
                        item.author != chatDataNextItem.author.id ||
                            (
                                item.author == formerItem?.author && item.timestamp.isDifferentMin(
                                    chatDataNextItem.timestamp,
                                )
                                )
                    }

                    if (formerItem != null && item.timestamp.isDifferentDay(formerItem.timestamp)
                    ) {
                        isFirst = true
                        chatData.add(
                            index = 0,
                            element = ChatDetailMessageState(
                                chatRoomId = item.chatRoomId,
                                type = ChatDetailChatTypeState.DATE,
                                timestamp = LocalDateTime.of(item.timestamp.year, item.timestamp.monthValue, item.timestamp.dayOfMonth, 0, 0),
                            ),
                        )
                    }
                    chatData.add(
                        index = 0,
                        element = item.toState(
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
                    message = chatData.sortedByDescending {
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

    companion object {
        const val PAGE_SIZE = 20
    }
}

internal fun LocalDateTime.isDifferentMin(time: LocalDateTime): Boolean {
    val seconds = abs(Duration.between(this, time).seconds)
    if (seconds >= 60) {
        return true
    }
    if (this.year != time.year) return true
    if (this.monthValue != time.monthValue) return true
    if (this.dayOfMonth != time.dayOfMonth) return true
    if (this.hour != time.hour) return true
    if (this.minute != time.minute) return true
    return false
}

internal fun LocalDateTime.isDifferentDay(time: LocalDateTime): Boolean = when {
    this.year != time.year -> true
    this.monthValue != time.monthValue -> true
    this.dayOfMonth != time.dayOfMonth -> true
    else -> false
}
