package com.seugi.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.notification.NotificationRepository
import com.seugi.notification.model.NotificationUiState
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
    private val notificationRepository: NotificationRepository,
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
        notificationRepository.getNotices(workspaceId).collect { result ->
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