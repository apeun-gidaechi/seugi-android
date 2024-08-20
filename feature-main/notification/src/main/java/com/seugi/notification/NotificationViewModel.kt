package com.seugi.notification

import android.util.Log
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.util.fastMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.notification.NotificationRepository
import com.seugi.data.notification.model.NotificationEmojiModel
import com.seugi.data.notification.model.NotificationModel
import com.seugi.notification.model.NotificationUiState
import com.seugi.notification.model.getEmojiList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.IDN
import java.time.LocalDateTime

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow(NotificationUiState())
    val state = _state.asStateFlow()

//    private val _debounce = MutableSharedFlow<>()

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
            size = PAGE_SIZE
        ).collect {
            when (it) {
                is Result.Success -> {

                    _state.update { state ->
                        state.copy(
                            notices = (state.notices + it.data)
                                .distinct()
                                .sortedBy {
                                    it.creationDate
                                }
                                .toImmutableList(),
                            nowPage = state.nowPage + 1
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
            size = PAGE_SIZE
        ).collect {
            when (it) {
                is Result.Success -> {

                    _state.update { state ->
                        state.copy(
                            notices = (state.notices + it.data)
                                .distinct()
                                .sortedBy {
                                    it.creationDate
                                }
                                .toImmutableList(),
                            nowPage = state.nowPage + 1,
                            isRefresh = false
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

    fun pressEmoji(id: Long, emoji: String, userId: Int) = viewModelScope.launch(dispatcher) {

        _state.update {
            it.copy(
                notices = it.notices.map { model ->
                    if (model.id != id) {
                        return@map model
                    }
                    val newEmojiList = model.emoji.toMutableList()
                    val emojiRemoved = newEmojiList.removeIf { item ->
                        item.emoji == emoji && item.userId == userId
                    }

                    if (!emojiRemoved) {
                        newEmojiList.add(
                            NotificationEmojiModel(emoji, userId)
                        )
                    }
                    model.copy(
                        emoji = newEmojiList.toImmutableList()
                    )
                }.toImmutableList()
            )
        }
    }
}


const val PAGE_SIZE = 20