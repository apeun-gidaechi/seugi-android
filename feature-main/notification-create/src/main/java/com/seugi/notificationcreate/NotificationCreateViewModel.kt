package com.seugi.notificationcreate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.notification.NotificationRepository
import com.seugi.notificationcreate.model.NotificationCreateUiState
import com.seugi.notificationcreate.model.NotificationSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationCreateViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    @SeugiDispatcher(DispatcherType.IO)  private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _state = MutableStateFlow(NotificationCreateUiState())
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<NotificationSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun create(
        title: String,
        content: String,
        workspaceId: String
    ) = viewModelScope.launch(dispatcher) {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        notificationRepository.createNotification(
            workspaceId = workspaceId,
            title = title,
            content = content
        ).collectLatest {
            when(it) {
                is Result.Success ->  {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _sideEffect.send(NotificationSideEffect.Success)
                }
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _sideEffect.send(NotificationSideEffect.Error(it.throwable))
                }
                is Result.Loading -> {}
            }
        }
    }
}