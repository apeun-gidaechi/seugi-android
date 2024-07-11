package com.apeun.gidaechi.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.common.utiles.DispatcherType
import com.apeun.gidaechi.common.utiles.SeugiDispatcher
import com.apeun.gidaechi.data.notice.NoticeRepository
import com.apeun.gidaechi.notification.model.NotificationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val noticeRepository: NoticeRepository,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow(NotificationUiState())
    val state = _state.asStateFlow()

    fun enabledRefresh() = viewModelScope.launch(dispatcher) {
        _state.update {
            it.copy(
                isRefresh = true,
            )
        }
    }

    fun loadNotices(workspaceId: String) = viewModelScope.launch(dispatcher) {
        noticeRepository.getNotices(workspaceId).collect { result ->
            when (result) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isRefresh = false,
                            notices = result.data.toImmutableList(),
                        )
                    }
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    result.throwable.printStackTrace()
                }
            }
        }
    }
}
