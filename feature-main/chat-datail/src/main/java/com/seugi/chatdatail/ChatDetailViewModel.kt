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
import com.seugi.common.utiles.toDeviceLocalDateTime
import com.seugi.common.utiles.toEpochMilli
import com.seugi.common.utiles.toMap
import com.seugi.data.core.model.ProfileModel
import com.seugi.data.core.model.UserInfoModel
import com.seugi.data.core.model.UserModel
import com.seugi.data.file.FileRepository
import com.seugi.data.file.model.FileType
import com.seugi.data.groupchat.GroupChatRepository
import com.seugi.data.message.MessageRepository
import com.seugi.data.message.model.MessageRoomEvent
import com.seugi.data.message.model.MessageRoomEvent.MessageParent
import com.seugi.data.message.model.MessageType
import com.seugi.data.message.model.copy
import com.seugi.data.message.model.getVisibleMessage
import com.seugi.data.message.model.stomp.MessageStompLifecycleModel
import com.seugi.data.personalchat.PersonalChatRepository
import com.seugi.data.profile.ProfileRepository
import com.seugi.data.token.TokenRepository
import com.seugi.data.workspace.WorkspaceRepository
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDateTime

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val personalChatRepository: PersonalChatRepository,
    private val groupChatRepository: GroupChatRepository,
    private val workspaceRepository: WorkspaceRepository,
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

    fun loadInfo(userId: Long, chatRoomId: String, isPersonal: Boolean, workspaceId: String) = viewModelScope.launch(Dispatchers.IO) {
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
        loadRoom(chatRoomId, isPersonal, userId, workspaceId)
    }

    fun loadMessage(chatRoomId: String, userId: Long) = viewModelScope.launch {
        if (state.value.isLastPage) return@launch
        val timestamp = if (state.value.isInit) state.value.message.lastOrNull()?.timestamp else null

        messageRepository.getMessage(
            chatRoomId = chatRoomId,
            timestamp = timestamp?.toKotlinLocalDateTime(),
            userId = userId,
        ).collect {
            when (it) {
                is Result.Success -> {
                    _state.update { uiState ->
                        uiState.copy(
                            isInit = true,
                            isLastPage = it.data.messages.size != 30,
                        )
                    }
                    it.data.messages.collectMessage()
                }
                Result.Loading -> {}
                is Result.Error -> {
                    it.throwable.printStackTrace()
                }
            }
        }
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

    private fun loadRoom(chatRoomId: String, isPersonal: Boolean, userId: Long, workspaceId: String) = viewModelScope.launch {
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
                    val users: MutableMap<Long, UserModel> = mutableMapOf()
                    it.data.memberList.forEach { user ->
                        users[user.userInfo.id] = user.userInfo
                    }
                    _state.value = _state.value.copy(
                        roomInfo = ChatRoomState(
                            id = chatRoomId,
                            roomName = it.data.chatName,
                            members = it.data.memberList
                                .map {
                                    if (it.userInfo.id == userId) {
                                        return@map it.copy(
                                            timestamp = LocalDateTime.MIN,
                                            utcTimeMillis = 0L,
                                        )
                                    }
                                    it
                                }
                                .sortedBy { it.utcTimeMillis }
                                .toImmutableList(),
                            adminId = it.data.roomAdmin,
                        ),
                        users = users.toImmutableMap(),
                    )

                    _state.update { state ->
                        state.copy(
                            message = state.message.map { messageParent ->
                                var newMessage = messageParent
                                if (messageParent is MessageParent.BOT.DrawLots) {
                                    newMessage = messageParent.copy(visibleMessage = messageParent.getVisibleMessage(state.roomInfo?.members))
                                }

                                if (messageParent is MessageParent.BOT.TeamBuild) {
                                    newMessage = messageParent.copy(visibleMessage = messageParent.getVisibleMessage(state.roomInfo?.members))
                                }
                                newMessage
                            }.toImmutableList(),
                        )
                    }

                    loadWorkspaceMembers(workspaceId)
                }

                is Result.Loading -> {}
                is Result.Error -> {
                    it.throwable.printStackTrace()
                }
            }
        }
    }

    private suspend fun loadWorkspaceMembers(workspaceId: String) {
        workspaceRepository.getMembers(workspaceId = workspaceId).collect {
            when (it) {
                is Result.Success -> {
                    _state.update { state ->
                        state.copy(
                            workspaceUsers = it.data.toImmutableList(),
                            workspaceUsersMap = it.data.toMap { it.member.id }.toImmutableMap(),
                        )
                    }
                }
                Result.Loading -> {}
                is Result.Error -> {
                    it.throwable.printStackTrace()
                }
            }
        }
    }

    fun memberInvite(chatRoomId: String, members: List<ProfileModel>) = viewModelScope.launch {
        groupChatRepository.addMembers(
            chatRoomId = chatRoomId,
            chatMemberUsers = members.map { it.member.id },
        ).collect {
            when (it) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            roomInfo = it.roomInfo?.copy(
                                members = it.roomInfo.members
                                    .toMutableList()
                                    .apply {
                                        addAll(
                                            members.map {
                                                UserInfoModel(
                                                    userInfo = it.member,
                                                    timestamp = LocalDateTime.of(2000, 1, 1, 1, 1),
                                                    utcTimeMillis = 3L,
                                                )
                                            },
                                        )
                                    }
                                    .toImmutableList(),
                            ),
                            users = it.users.toMutableMap()
                                .apply {
                                    putAll(
                                        members
                                            .map { it.member }
                                            .toMap { it.id },
                                    )
                                }
                                .toImmutableMap(),
                        )
                    }
                }
                Result.Loading -> {
                }
                is Result.Error -> {
                    it.throwable.printStackTrace()
                }
            }
        }
    }

    fun collectStompLifecycle(chatRoomId: String, userId: Long) {
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
//                                    val exception = data.exception as? Exception
                                    Log.d("TAG", "collectStompLifecycle: ${data.exception}")
                                    when (data.exception) {
                                        is StompException -> {
                                            tokenRepository.newToken().collect {
                                                when (it) {
                                                    is Result.Success -> {
                                                        messageRepository.getMessage(
                                                            chatRoomId = chatRoomId,
                                                            userId = userId,
                                                            timestamp = LocalDateTime.now().toKotlinLocalDateTime(),
                                                        ).collect {
                                                            when (it) {
                                                                is Result.Success -> {
                                                                    _state.update { uiState ->
                                                                        uiState.copy(
                                                                            isInit = true,
                                                                            isLastPage = it.data.messages.size != 30,
                                                                        )
                                                                    }
                                                                    it.data.messages.collectMessage()
                                                                }
                                                                Result.Loading -> {}
                                                                is Result.Error -> {
                                                                    it.throwable.printStackTrace()
                                                                }
                                                            }
                                                        }
                                                        channelReconnect(userId)
                                                    }
                                                    else -> {}
                                                }
                                            }
                                        }
                                        is SocketException -> {
                                            data.exception.printStackTrace()
//                                            Log.d("TAG", "collectStompLifecycle: $data")
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

    fun channelSend(userId: Long, content: String, uuid: String = UUID.randomUUID().toString(), type: MessageType, mention: List<Long> = emptyList()) {
        viewModelScope.launch {
            // 파일, 이미지는 socket 서버 업로드전에 생김
            if (type == MessageType.MESSAGE) {
                _messageSaveQueueState.value += ChatLocalType.SendText(content, mention, uuid)
            }

            val result = messageRepository.sendMessage(
                chatRoomId = state.value.roomInfo?.id ?: "",
                message = content,
                messageUUID = uuid,
                type = type,
                mention = mention,
            )
            Log.d("TAG", "testSend: $result")
            when (result) {
                is Result.Success -> {
                    if (!result.data) {
                        failedSend(
                            type = type,
                            uuid = uuid,
                            content = content,
                            mention = mention,
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

    fun channelSend(userId: Long, image: Bitmap, fileName: String, uuid: String = UUID.randomUUID().toString()) {
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

    fun channelSend(userId: Long, fileByteArray: ByteArray, fileMimeType: String, fileName: String, fileByte: Long) {
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

    fun channelResend(userId: Long, content: String, uuid: String, type: MessageType = MessageType.MESSAGE, mention: List<Long> = emptyList()) {
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
        channelSend(userId, content, uuid, type, mention)
    }

    fun channelResend(userId: Long, fileName: String, fileMimeType: String, fileByteArray: ByteArray, fileByte: Long, uuid: String) {
        _messageSaveQueueState.value -= uuid
        channelSend(
            userId = userId,
            fileName = fileName,
            fileMimeType = fileMimeType,
            fileByteArray = fileByteArray,
            fileByte = fileByte,
        )
    }

    fun channelReSend(userId: Long, image: Bitmap, fileName: String, uuid: String) {
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

    fun channelReconnect(userId: Long, roomId: String? = null) {
        viewModelScope.launch {
            isReconnectTry = true
            subscribeChat?.cancel()
            val job = viewModelScope.async {
                messageRepository.reSubscribeRoom(
                    chatRoomId = roomId ?: state.value.roomInfo?.id ?: "",
                    userId = userId,
                ).collect {
                    it.collectMessage()
                }
            }
            subscribeChat = job
            job.await()
//            Log.d("TAG", "next Channel Connect: ")
//            channelConnect(userId)
//            Log.d("TAG", "testReconnect: ")
        }
    }

    private fun channelConnect(userId: Long) {
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

    private fun List<MessageParent>.collectMessage() {
        // 생길 수 있는 문제점.
        // 최상단에 있는 Date와 충돌할 가능성이 있다. -> Date라면 지우고 추가 달리기
        // 최상단에 있는 채팅의 isFirst 변경해야할 수 있음 (둘다 유저가 같다면)
        // 데이터 불러올 때 날짜 비교해서 사이에 끼워넣기

        if (this.isEmpty()) {
            return
        }

        val data = this
            .sortedByDescending { it.timestamp }
            .toMutableList()
        val message = _state.value.message.toMutableList()

        val remoteFirstItem = data.first()
        val lastItem = message.lastOrNull()

        // 날짜가 같다면 삭제
        if (lastItem is MessageParent.Date && remoteFirstItem.timestamp.isDifferentDay(lastItem.timestamp).not()) {
            message.removeLast()
        }

        // 최상단에 있는 채팅 isFirst 놔둬야 할지 비교
        if (lastItem is MessageParent.Other && lastItem.isFirst && remoteFirstItem.userId == lastItem.userId) {
            message.removeLast()
            message.add(
                lastItem.copy(
                    isFirst = false,
                ),
            )
        }

        data.forEachIndexed { index, messageParent ->

            val formerItem = data.getOrNull(index + 1)
            val nextItem = if (index == 0) message.lastOrNull() else data.getOrNull(index - 1)

            var isFirst = messageParent.userId != formerItem?.userId
            val isLast = messageParent.userId != nextItem?.userId ||
                messageParent.timestamp.isDifferentMin(nextItem.timestamp) ||
                formerItem is MessageParent.Enter || formerItem is MessageParent.Left

            if (formerItem != null && messageParent.timestamp.isDifferentDay(formerItem.timestamp)) {
                isFirst = true
            }

            if (formerItem is MessageParent.Enter || formerItem is MessageParent.Left) {
                isFirst = true
            }

            val newData = when (messageParent) {
                is MessageParent.Me -> messageParent.copy(
                    isLast = isLast,
                )
                is MessageParent.Other -> {
                    messageParent.copy(
                        isLast = isLast,
                        isFirst = isFirst,
                    )
                }

                is MessageParent.File -> {
                    messageParent.copy(
                        isLast = isLast,
                        isFirst = isFirst,
                    )
                }
                is MessageParent.Img -> {
                    messageParent.copy(
                        isLast = isLast,
                        isFirst = isFirst,
                    )
                }

                is MessageParent.BOT -> {
                    if (messageParent is MessageParent.BOT.DrawLots) {
                        messageParent.copy(
                            visibleMessage = messageParent.getVisibleMessage(state.value.roomInfo?.members),
                            isLast = isLast,
                            isFirst = isFirst,
                        )
                    } else if (messageParent is MessageParent.BOT.TeamBuild) {
                        messageParent.copy(
                            visibleMessage = messageParent.getVisibleMessage(state.value.roomInfo?.members),
                            isLast = isLast,
                            isFirst = isFirst,
                        )
                    } else {
                        messageParent.copy(
                            isLast = isLast,
                            isFirst = isFirst,
                        )
                    }
                }
                else -> messageParent
            }

            message.add(newData)

            if (formerItem != null && messageParent.timestamp.isDifferentDay(formerItem.timestamp)) {
                message.add(
                    element = MessageParent.Date(
                        type = MessageType.MESSAGE,
                        timestamp = LocalDateTime.of(messageParent.timestamp.year, messageParent.timestamp.monthValue, messageParent.timestamp.dayOfMonth, 0, 0),
                        userId = 0,
                        text = "",
                    ),
                )
            }
        }

        _state.update {
            it.copy(
                message = message
                    .distinct()
                    .toImmutableList(),
            )
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
                        formerItem is MessageParent.Other && formerItem.isLast && formerItem.userId == data.userId && !formerItem.timestamp.isDifferentMin(data.timestamp)
                    ) {
                        message[0] = formerItem.copy(isLast = false)
                    }
                    if (
                        formerItem is MessageParent.File && formerItem.isLast && formerItem.userId == data.userId && !formerItem.timestamp.isDifferentMin(data.timestamp)
                    ) {
                        message[0] = formerItem.copy(isLast = false)
                    }

                    if (
                        formerItem is MessageParent.Img && formerItem.isLast && formerItem.userId == data.userId && !formerItem.timestamp.isDifferentMin(data.timestamp)
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
                        formerItem is MessageParent.File && formerItem.isLast && formerItem.userId == data.userId && !formerItem.timestamp.isDifferentMin(data.timestamp)
                    ) {
                        message[0] = formerItem.copy(isLast = false)
                    }
                    if (
                        formerItem is MessageParent.Img && formerItem.isLast && formerItem.userId == data.userId && !formerItem.timestamp.isDifferentMin(data.timestamp)
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

                is MessageParent.BOT -> {
                    var data = data as MessageParent.BOT

                    val message = _state.value.message.toMutableList()
                    val formerItem = message.firstOrNull()
                    if (
                        formerItem is MessageParent.BOT && formerItem.isLast && formerItem.userId == data.userId && !formerItem.timestamp.isDifferentMin(data.timestamp)
                    ) {
                        if (formerItem is MessageParent.BOT.Meal) {
                            message[0] = formerItem.copy(isLast = false)
                        }
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

                    if (data is MessageParent.BOT.DrawLots && state.value.roomInfo?.members?.isNotEmpty() == true) {
                        data = data.copy(
                            visibleMessage = data.getVisibleMessage(state.value.roomInfo?.members),
                        )
                    }

                    if (data is MessageParent.BOT.TeamBuild && state.value.roomInfo?.members?.isNotEmpty() == true) {
                        data = data.copy(
                            visibleMessage = data.getVisibleMessage(state.value.roomInfo?.members),
                        )
                    }
                    message.add(
                        index = 0,
                        element = data.copy(
                            isLast = true,
                        ),
                    )

                    _state.update {
                        it.copy(
                            message = message.toImmutableList(),
                        )
                    }
                }

                is MessageParent.Img -> {
                    val data = data as MessageParent.Img
                    val message = _state.value.message.toMutableList()
                    val formerItem = message.firstOrNull()

                    var isFirst = data.userId != formerItem?.userId
                    if (
                        formerItem is MessageParent.Other && formerItem.isLast && formerItem.userId == data.userId && !formerItem.timestamp.isDifferentMin(data.timestamp)
                    ) {
                        message[0] = formerItem.copy(isLast = false)
                    }

                    if (
                        formerItem is MessageParent.File && formerItem.isLast && formerItem.userId == data.userId && !formerItem.timestamp.isDifferentMin(data.timestamp)
                    ) {
                        message[0] = formerItem.copy(isLast = false)
                    }

                    if (
                        formerItem is MessageParent.Img && formerItem.isLast && formerItem.userId == data.userId && !formerItem.timestamp.isDifferentMin(data.timestamp)
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

                is MessageParent.File -> {
                    val data = data as MessageParent.File
                    val message = _state.value.message.toMutableList()
                    val formerItem = message.firstOrNull()

                    var isFirst = data.userId != formerItem?.userId
                    if (
                        formerItem is MessageParent.Other && formerItem.isLast && formerItem.userId == data.userId && !formerItem.timestamp.isDifferentMin(data.timestamp)
                    ) {
                        message[0] = formerItem.copy(isLast = false)
                    }
                    if (
                        formerItem is MessageParent.File && formerItem.isLast && formerItem.userId == data.userId && !formerItem.timestamp.isDifferentMin(data.timestamp)
                    ) {
                        message[0] = formerItem.copy(isLast = false)
                    }

                    if (
                        formerItem is MessageParent.Img && formerItem.isLast && formerItem.userId == data.userId && !formerItem.timestamp.isDifferentMin(data.timestamp)
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
                            roomInfo = it.roomInfo?.copy(
                                members = it.roomInfo.members
                                    .toMutableList()
                                    .map { userInfo ->
                                        if (userInfo.userInfo.id != data.userId) {
                                            return@map userInfo
                                        }
                                        userInfo.copy(
                                            userInfo = userInfo.userInfo,
                                            timestamp = LocalDateTime.MIN,
                                            utcTimeMillis = 0L,
                                        )
                                    }
                                    .sortedBy { it.utcTimeMillis }
                                    .toImmutableList(),
                            ),
                        )
                    }
                }
                is MessageRoomEvent.UnSub -> {
                    Log.d("TAG", "collectMessage: UNSUB User")
                    _state.update {
                        it.copy(
                            roomInfo = it.roomInfo?.copy(
                                members = it.roomInfo.members
                                    .toMutableList()
                                    .map { userInfo ->
                                        if (userInfo.userInfo.id != data.userId) {
                                            return@map userInfo
                                        }
                                        val timestamp = LocalDateTime.now()
                                        Log.d(
                                            "TAG",
                                            "collectMessage: UNSUB ${userInfo.copy(
                                                userInfo = userInfo.userInfo,
                                                timestamp = timestamp,
                                                utcTimeMillis = timestamp.toEpochMilli(),
                                            )}",
                                        )
                                        userInfo.copy(
                                            userInfo = userInfo.userInfo,
                                            timestamp = timestamp,
                                            utcTimeMillis = timestamp.toEpochMilli(),
                                        )
                                    }
                                    .sortedBy { it.utcTimeMillis }
                                    .toImmutableList(),
                            ),
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

    fun getPersonalChat(workspaceId: String, userId: Long) = viewModelScope.launch {
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

    private fun failedSend(type: MessageType, uuid: String, content: String, mention: List<Long> = emptyList()) {
        _messageSaveQueueState.value -= uuid
        if (type == MessageType.MESSAGE) {
            _messageSaveQueueState.value += ChatLocalType.FailedText(content, mention, uuid)
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
        Log.d("TAG", "isDifferentMin: $seconds")
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

internal fun MessageParent.getUserCount(users: List<UserInfoModel>): ImmutableList<Long> {
    val utcTimeMillis = this.timestamp.toDeviceLocalDateTime().toEpochMilli()
    val readUsers = mutableListOf<Long>()

    // 현재 접속중인 유저수 세기
    users.takeWhile {
        if (it.utcTimeMillis == 0L) {
            readUsers.add(it.userInfo.id)
        }
        it.utcTimeMillis == 0L
    }

    // 해당 메세지를 읽은 유저 카운트
    val binaryIndex = users.binarySearch {
        when {
            it.utcTimeMillis >= utcTimeMillis -> 0
            else -> -1
        }
    }
    val index = if (binaryIndex >= 0) binaryIndex else users.size
    for (i in index until users.size) {
        val user = users.getOrNull(i)
        if (user != null) {
            readUsers.add(user.userInfo.id)
        }
    }
    return readUsers.toImmutableList()
}
