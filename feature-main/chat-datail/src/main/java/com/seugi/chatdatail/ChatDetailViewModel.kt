package com.seugi.chatdatail

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.chatdatail.model.ChatDetailSideEffect
import com.seugi.chatdatail.model.ChatDetailUiState
import com.seugi.chatdatail.model.ChatLocalType
import com.seugi.chatdatail.model.ChatRoomState
import com.seugi.chatdatail.model.containsWithUUID
import com.seugi.chatdatail.model.minus
import com.seugi.chatdatail.model.plus
import com.seugi.common.model.Result
import com.seugi.data.core.model.UserModel
import com.seugi.data.file.FileRepository
import com.seugi.data.file.model.FileType
import com.seugi.data.groupchat.GroupChatRepository
import com.seugi.data.message.MessageRepository
import com.seugi.data.message.model.MessageRoomEvent
import com.seugi.data.message.model.MessageRoomEvent.MessageParent
import com.seugi.data.message.model.MessageType
import com.seugi.data.message.model.stomp.MessageStompLifecycleModel
import com.seugi.data.personalchat.PersonalChatRepository
import com.seugi.data.profile.ProfileRepository
import com.seugi.data.token.TokenRepository
import com.seugi.stompclient.StompException
import com.seugi.ui.toByteArray
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.SocketException
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject
import kotlin.math.abs
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val personalChatRepository: PersonalChatRepository,
    private val groupChatRepository: GroupChatRepository,
    private val profileRepository: ProfileRepository,
    private val tokenRepository: TokenRepository,
    private val fileRepository: FileRepository,
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

    private var isReconnectTry: Boolean = false

    fun loadInfo(userId: Int, chatRoomId: String, isPersonal: Boolean, workspaceId: String) = viewModelScope.launch(Dispatchers.IO) {
        _state.value = _state.value.copy(
            userInfo = UserModel(
                id = userId,
                email = "",
                birth = "",
                name = "",
                picture = "",
            ),
            roomInfo = ChatRoomState(
                chatRoomId,
                "",
                members = persistentListOf(),
                0,
            ),
        )
        initProfile(workspaceId)
        loadRoom(chatRoomId, isPersonal)
    }

    private fun initProfile(workspaceId: String) = viewModelScope.launch {
        profileRepository.getProfile(workspaceId).collect {
            when (it) {
                is Result.Success -> {
                    _state.update { state ->
                        state.copy(
                            userInfo = it.data.member,
                        )
                    }
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    it.throwable.printStackTrace()
                }
            }
        }
    }

    private fun loadRoom(chatRoomId: String, isPersonal: Boolean) = viewModelScope.launch {
        val result = if (isPersonal) {
            personalChatRepository.getChat(
                roomId = chatRoomId,
            )
        } else {
            groupChatRepository.getGroupRoom(
                roomId = chatRoomId,
            )
        }
        result.collect {
            when (it) {
                is Result.Success -> {
                    val users: MutableMap<Int, UserModel> = mutableMapOf()
                    it.data.memberList.forEach { user ->
                        users[user.id] = user
                    }
                    _state.value = _state.value.copy(
                        roomInfo = ChatRoomState(
                            id = chatRoomId,
                            roomName = it.data.chatName,
                            members = it.data.memberList.toImmutableList(),
                            adminId = it.data.roomAdmin,
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

    fun collectStompLifecycle(userId: Int) {
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
                                                        // TODO 페이징 처리
//                                                        messageRepository.getMessage(state.value.roomInfo?.id ?: "665d9ec15e65717b19a62701", 0, PAGE_SIZE).collect {
//                                                            it.collectMessage()
//                                                        }
                                                        channelReconnect(userId)
                                                    }
                                                    else -> {}
                                                }
                                            }
                                        }
                                        is SocketException -> {
                                            channelReconnect(userId)
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

    fun channelSend(userId: Int, content: String, uuid: String = UUID.randomUUID().toString(), type: MessageType) {
        viewModelScope.launch {
            // 파일, 이미지는 socket 서버 업로드전에 생김
            if (type == MessageType.MESSAGE) {
                _messageSaveQueueState.value += ChatLocalType.SendText(content, uuid)
            }

            val result = messageRepository.sendMessage(
                chatRoomId = state.value.roomInfo?.id ?: "",
                message = content,
                messageUUID = uuid,
                type = type,
            )
            Log.d("TAG", "testSend: $result")
            when (result) {
                is Result.Success -> {
                    if (!result.data) {
                        failedSend(
                            type = type,
                            uuid = uuid,
                            content = content,
                        )
                        channelReconnect(userId)
                        return@launch
                    }
                    // 메세지가 보내고 MESSAGE_TIMEOUT 시간만큼 지났는데도 안보내지면 실패처리
                    delay(MESSAGE_TIMEOUT)
                    if (_messageSaveQueueState.value.containsWithUUID(uuid)) {
                        failedSend(
                            type = type,
                            uuid = uuid,
                            content = content,
                        )

                        // 전송후 실패했다면, 소켓 재연결
                        channelReconnect(userId)
                    }
                }
                is Result.Error -> {
                    result.throwable.printStackTrace()
                    failedSend(
                        type = type,
                        uuid = uuid,
                        content = content,
                    )
                }
                is Result.Loading -> {}
            }
        }
    }

    fun channelSend(userId: Int, image: Bitmap, fileName: String, uuid: String = UUID.randomUUID().toString()) {
        viewModelScope.launch {
            _messageSaveQueueState.value += ChatLocalType.SendImg(
                image = image,
                fileName = fileName,
                uuid = uuid,
            )
            fileRepository.fileUpload(
                type = FileType.IMG,
                fileName = fileName,
                fileMimeType = "image/*",
                fileByteArray = image.toByteArray(),
            ).collect {
                when (it) {
                    is Result.Success -> {
                        channelSend(
                            userId = userId,
                            content = "${it.data.url}::$fileName",
                            type = MessageType.IMG,
                            uuid = uuid,
                        )
                    }
                    Result.Loading -> {}
                    is Result.Error -> {
                        _messageSaveQueueState.value -= uuid
                        _messageSaveQueueState.value += ChatLocalType.FailedImgUpload(
                            image = image,
                            fileName = fileName,
                            uuid = uuid,
                        )

                        it.throwable.printStackTrace()
                    }
                }
            }
        }
    }

    fun channelSend(userId: Int, fileByteArray: ByteArray, fileMimeType: String, fileName: String, fileByte: Long) {
        viewModelScope.launch {
            val uuid = UUID.randomUUID().toString()

            _messageSaveQueueState.value += ChatLocalType.SendFile(
                fileByteArray = fileByteArray,
                fileMimeType = fileMimeType,
                fileName = fileName,
                fileByte = fileByte,
                uuid = uuid,
            )
            fileRepository.fileUpload(
                type = FileType.FILE,
                fileName = fileName,
                fileMimeType = fileMimeType,
                fileByteArray = fileByteArray,
            ).collect {
                when (it) {
                    is Result.Success -> {
                        channelSend(
                            userId = userId,
                            content = "${it.data.url}::$fileName::$fileByte",
                            type = MessageType.FILE,
                            uuid = uuid,
                        )
                    }
                    Result.Loading -> {}
                    is Result.Error -> {
                        _messageSaveQueueState.value -= uuid
                        _messageSaveQueueState.value += ChatLocalType.FailedFileUpload(
                            fileByteArray = fileByteArray,
                            fileMimeType = fileMimeType,
                            fileName = fileName,
                            fileByte = fileByte,
                            uuid = uuid,
                        )
                        it.throwable.printStackTrace()
                    }
                }
            }
        }
    }

    fun channelResend(userId: Int, content: String, uuid: String, type: MessageType = MessageType.MESSAGE) {
        _messageSaveQueueState.value -= uuid
        when {
            type == MessageType.FILE -> {
                val files = content.split("::")
                _messageSaveQueueState.value += ChatLocalType.SendFileUrl(
                    fileUrl = files[0],
                    fileName = files[1],
                    fileByte = files[2].toLong(),
                    uuid = uuid,
                )
            }
            type == MessageType.IMG -> {
                val files = content.split("::")
                _messageSaveQueueState.value += ChatLocalType.SendImgUrl(
                    image = files[0],
                    fileName = files[1],
                    uuid = uuid,
                )
            }
        }
        channelSend(userId, content, uuid, type)
    }

    fun channelResend(userId: Int, fileName: String, fileMimeType: String, fileByteArray: ByteArray, fileByte: Long, uuid: String) {
        _messageSaveQueueState.value -= uuid
        channelSend(
            userId = userId,
            fileName = fileName,
            fileMimeType = fileMimeType,
            fileByteArray = fileByteArray,
            fileByte = fileByte,
        )
    }

    fun channelReSend(userId: Int, image: Bitmap, fileName: String, uuid: String) {
        _messageSaveQueueState.value -= uuid
        channelSend(
            userId = userId,
            image = image,
            fileName = fileName,
            uuid = uuid,
        )
    }

    fun deleteFailedSend(uuid: String) {
        _messageSaveQueueState.value -= uuid
    }

    fun channelReconnect(userId: Int) {
        viewModelScope.launch {
            isReconnectTry = true
            subscribeChat?.cancel()
            val job = viewModelScope.async {
                messageRepository.reSubscribeRoom(
                    chatRoomId = state.value.roomInfo?.id ?: "",
                    userId = userId,
                ).collect {
                    it.collectMessage()
                }
            }
            subscribeChat = job
            job.await()
            Log.d("TAG", "next Channel Connect: ")
            channelConnect(userId)
            Log.d("TAG", "testReconnect: ")
        }
    }

    private fun channelConnect(userId: Int) {
        viewModelScope.launch {
            subscribeChat?.cancel()
            val job = viewModelScope.async {
                messageRepository.subscribeRoom(
                    chatRoomId = state.value.roomInfo?.id ?: "",
                    userId = userId,
                ).collect {
                    it.collectMessage()
                }
            }
            subscribeChat = job
            isReconnectTry = false
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
            groupChatRepository.leftRoom(chatRoomId).collect {
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

    private fun Result<MessageRoomEvent>.collectMessage() = when (this) {
        is Result.Success -> {
            when (data) {
                is MessageParent.Other -> {
                    val data = data as MessageParent.Other
                    val message = _state.value.message.toMutableList()
                    val formerItem = message.firstOrNull()

                    var isFirst = data.userId != formerItem?.userId
                    if (
                        formerItem is MessageParent.Me && formerItem.isLast && formerItem.userId == data.userId && !formerItem.timestamp.isDifferentMin(data.timestamp)
                    ) {
                        message[0] = formerItem.copy(isLast = false)
                    }
                    if (
                        formerItem is MessageParent.Other && formerItem.isLast && formerItem.userId == data.userId && !formerItem.timestamp.isDifferentMin(data.timestamp)
                    ) {
                        message[0] = formerItem.copy(isLast = false)
                    }

                    if (formerItem != null && data.timestamp.isDifferentDay(formerItem.timestamp)) {
                        isFirst = true
                        message.add(
                            index = 0,
                            element = MessageParent.Date(
                                type = MessageType.MESSAGE,
                                timestamp = LocalDateTime.of(data.timestamp.year, data.timestamp.monthValue, data.timestamp.dayOfMonth, 0, 0),
                                userId = 0,
                                text = "",
                            ),
                        )
                    }
                    message.add(
                        index = 0,
                        element = data.copy(
                            isLast = true,
                            isFirst = isFirst,
                        ),
                    )

                    // messageQueue 삭제 로직
                    if (data.userId == _state.value.userInfo?.id) {
                        _messageSaveQueueState.value -= data.uuid ?: ""
                    }
                    _messageSaveQueueState.value -= data.uuid ?: ""
                    _state.update {
                        it.copy(
                            message = message.toImmutableList(),
                        )
                    }
                }

                is MessageParent.Me -> {
                    val data = data as MessageParent.Me
                    val message = _state.value.message.toMutableList()
                    val formerItem = message.firstOrNull()
                    if (
                        formerItem is MessageParent.Me && formerItem.isLast && formerItem.userId == data.userId && !formerItem.timestamp.isDifferentMin(data.timestamp)
                    ) {
                        message[0] = formerItem.copy(isLast = false)
                    }
                    if (
                        formerItem is MessageParent.Other && formerItem.isLast && formerItem.userId == data.userId && !formerItem.timestamp.isDifferentMin(data.timestamp)
                    ) {
                        message[0] = formerItem.copy(isLast = false)
                    }

                    if (formerItem != null && data.timestamp.isDifferentDay(formerItem.timestamp)) {
                        message.add(
                            index = 0,
                            element = MessageParent.Date(
                                type = MessageType.MESSAGE,
                                timestamp = LocalDateTime.of(data.timestamp.year, data.timestamp.monthValue, data.timestamp.dayOfMonth, 0, 0),
                                userId = 0,
                                text = "",
                            ),
                        )
                    }
                    _messageSaveQueueState.value -= data.uuid ?: ""
                    message.add(
                        index = 0,
                        element = data.copy(
                            isLast = true,
                        ),
                    )

                    // messageQueue 삭제 로직
                    if (data.userId == _state.value.userInfo?.id) {
                        _messageSaveQueueState.value -= data.uuid ?: ""
                    }
                    _state.update {
                        it.copy(
                            message = message.toImmutableList(),
                        )
                    }
                }

                is MessageParent.Img -> {
                    val data = data as MessageParent.Img
                    // messageQueue 삭제 로직
                    if (data.userId == _state.value.userInfo?.id) {
                        _messageSaveQueueState.value -= data.uuid ?: ""
                    }
                    _messageSaveQueueState.value -= data.uuid ?: ""
                    _state.update {
                        it.copy(
                            message = it.message.toMutableList().apply {
                                add(0, data)
                            }.toImmutableList(),
                        )
                    }
                }

                is MessageParent.File -> {
                    val data = data as MessageParent.File
                    // messageQueue 삭제 로직
                    if (data.userId == _state.value.userInfo?.id) {
                        _messageSaveQueueState.value -= data.uuid ?: ""
                    }
                    _messageSaveQueueState.value -= data.uuid ?: ""
                    _state.update {
                        it.copy(
                            message = it.message.toMutableList().apply {
                                add(0, data)
                            }.toImmutableList(),
                        )
                    }
                }

                is MessageParent.Enter -> {
                    val data = data as MessageParent.Enter
                    _state.update {
                        it.copy(
                            message = it.message.toMutableList().apply {
                                add(0, data)
                            }.toImmutableList(),
                        )
                    }
                }

                is MessageParent.Left -> {
                    val data = data as MessageParent.Left
                    _state.update {
                        it.copy(
                            message = it.message.toMutableList().apply {
                                add(0, data)
                            }.toImmutableList(),
                        )
                    }
                }

                is MessageParent.Etc -> {
                    val data = data as MessageParent.Etc
                    _state.update {
                        it.copy(
                            message = it.message.toMutableList().apply {
                                add(0, data)
                            }.toImmutableList(),
                        )
                    }
                }

                is MessageRoomEvent.Sub -> {
                    _state.update {
                        it.copy(
                            message = it.message.map { nowMessage ->
                                when (nowMessage) {
                                    is MessageParent.Me ->
                                        nowMessage.copy(
                                            read = nowMessage.read
                                                .toMutableList()
                                                .apply {
                                                    add(data.userId)
                                                }
                                                .distinct()
                                                .toImmutableList(),
                                        )

                                    is MessageParent.Other ->
                                        nowMessage.copy(
                                            read = nowMessage.read
                                                .toMutableList()
                                                .apply {
                                                    add(data.userId)
                                                }
                                                .distinct()
                                                .toImmutableList(),
                                        )

                                    else -> nowMessage
                                }
                            }.toImmutableList(),
                        )
                    }
                }

                else -> {}
            }
        }
        Result.Loading -> {}
        is Result.Error -> {
            throwable.printStackTrace()
        }
    }

    companion object {
        const val PAGE_SIZE = 20
        const val MESSAGE_TIMEOUT = 6000L
    }

    fun getPersonalChat(workspaceId: String, userId: Int) = viewModelScope.launch {
        // 개인 채팅방이 있을 경우 존재하는 방을 리턴하므로 중복 X
        personalChatRepository.createChat(
            workspaceId = workspaceId,
            roomName = "",
            joinUsers = listOf(userId),
            chatRoomImg = "",
        ).collect {
            when (it) {
                is Result.Success -> {
                    _sideEffect.send(ChatDetailSideEffect.SuccessMoveRoom(it.data))
                }
                Result.Loading -> {}
                is Result.Error -> {
                    it.throwable.printStackTrace()
                    _sideEffect.send(ChatDetailSideEffect.FailedMoveRoom(it.throwable))
                }
            }
        }
    }

    private fun failedSend(type: MessageType, uuid: String, content: String) {
        _messageSaveQueueState.value -= uuid
        if (type == MessageType.MESSAGE) {
            _messageSaveQueueState.value += ChatLocalType.FailedText(content, uuid)
        } else if (type == MessageType.FILE) {
            val files = content.split("::")
            _messageSaveQueueState.value += ChatLocalType.FailedFileSend(
                fileUrl = files[0],
                fileName = files[1],
                fileByte = files[2].toLong(),
                uuid = uuid,
            )
        } else if (type == MessageType.IMG) {
            val files = content.split("::")
            _messageSaveQueueState.value += ChatLocalType.FailedImgSend(
                image = files[0],
                fileName = files[1],
                uuid = uuid,
            )
        }
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
