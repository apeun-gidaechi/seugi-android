package com.seugi.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.notification.NotificationRepository
import com.seugi.data.notification.model.NotificationEmojiModel
import com.seugi.notification.model.NotificationSideEffect
import com.seugi.notification.model.NotificationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class NotificationDebounce(
    open val id: Long,
    open val emoji: String,
) {
    data class Add(override val id: Long, override val emoji: String) : NotificationDebounce(id, emoji)
    data class Remove(override val id: Long, override val emoji: String) : NotificationDebounce(id, emoji)
}

@OptIn(FlowPreview::class)
@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow(NotificationUiState())
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<NotificationSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun enabledRefresh() = viewModelScope.launch(dispatcher) {
        _state.update {
            it.copy(
                isRefresh = true,
            )
        }
    }

    fun nextPage(workspaceId: String) = viewModelScope.launch {
        notificationRepository.getNotices(
            workspaceId = workspaceId,
            page = _state.value.nowPage,
            size = PAGE_SIZE,
        ).collect {
            when (it) {
                is Result.Success -> {
                    _state.update { state ->
                        state.copy(
                            notices = (it.data + state.notices)
                                .distinctBy {
                                    Pair(it.workspaceId, it.id)
                                }
                                .sortedBy {
                                    it.creationDate
                                }
                                .toImmutableList(),
                            nowPage = state.nowPage + 1,
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

    fun refreshFirstPage(workspaceId: String) = viewModelScope.launch(dispatcher) {
        notificationRepository.getNotices(
            workspaceId = workspaceId,
            page = 0,
            size = PAGE_SIZE,
        ).collect {
            when (it) {
                is Result.Success -> {
                    _state.update { state ->
                        state.copy(
                            notices = (it.data + state.notices)
                                .distinctBy {
                                    Pair(it.workspaceId, it.id)
                                }
                                .sortedBy {
                                    it.creationDate
                                }
                                .toImmutableList(),
                            nowPage = state.nowPage + 1,
                            isRefresh = false,
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

    fun pressEmoji(id: Long, emoji: String, userId: Long) = viewModelScope.launch(dispatcher) {
        _state.update {
            it.copy(
                notices = it.notices.map { model ->
                    if (model.id != id) {
                        return@map model
                    }
                    var isChanged = false

                    val newEmojiList: MutableList<NotificationEmojiModel> = model.emoji.toMutableList()

                    var changeIndex = 0
                    var changeItem: NotificationEmojiModel? = null

                    newEmojiList.forEachIndexed { index, notificationEmojiModel ->

                        // 해당 하는 이모지가 아니라면 리턴
                        if (notificationEmojiModel.emoji != emoji) return@forEachIndexed

                        isChanged = true

                        val newUserList = notificationEmojiModel.userList.toMutableList()
                        val emojiRemoved = newUserList.removeIf { item ->
                            item == userId
                        }
                        if (!emojiRemoved) {
                            newUserList.add(userId)
                        }

                        changeIndex = index
                        if (newUserList.isNotEmpty()) {
                            changeItem = NotificationEmojiModel(
                                emoji = emoji,
                                userList = newUserList.toImmutableList(),
                            )
                        }
                    }

                    // 기존에 없던 새로운 이모지라면
                    if (!isChanged) {
                        newEmojiList.add(
                            NotificationEmojiModel(
                                emoji = emoji,
                                userList = persistentListOf(userId),
                            ),
                        )
                    } else {
                        // forEach 안에서 할 경우 Exeption 발생
                        if (changeItem == null) {
                            newEmojiList.removeAt(changeIndex)
                        } else {
                            newEmojiList[changeIndex] = changeItem!!
                        }
                    }
                    model.copy(
                        emoji = newEmojiList.toImmutableList(),
                    )
                }.toImmutableList(),
            )
        }
        notificationRepository.pathEmoji(
            emoji = emoji,
            notificationId = id,
        ).collectLatest {
        }
    }

    fun deleteNotification(workspaceId: String, notificationId: Long) = viewModelScope.launch(dispatcher) {
        notificationRepository.deleteNotice(
            workspaceId = workspaceId,
            notificationId = notificationId,
        ).collect {
            when (it) {
                is Result.Success -> {
                    if (!it.data) {
                        return@collect
                    }
                    _state.update { state ->
                        state.copy(
                            notices = state.notices
                                .toMutableList()
                                .apply {
                                    removeIf {
                                        it.id == notificationId && it.workspaceId == workspaceId
                                    }
                                }
                                .toImmutableList(),
                        )
                    }
                }
                is Result.Error -> {
                    _sideEffect.send(NotificationSideEffect.Error(it.throwable))
                }
                is Result.Loading -> {}
            }
        }
    }
}

const val PAGE_SIZE = 20
