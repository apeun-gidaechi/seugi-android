package com.seugi.notificationedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.notification.NotificationRepository
import com.seugi.notificationedit.model.NotificationEditUiState
import com.seugi.notificationedit.model.NotificationSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationEditViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _state = MutableStateFlow(NotificationEditUiState())
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<NotificationSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun edit(id: Long, title: String, content: String) = viewModelScope.launch(dispatcher) {
        setLoadingState(true)
        notificationRepository.patchNotice(
            title = title,
            content = content,
            notificationId = id
        ).collect {
            when (it) {
                is Result.Success -> {
                    setLoadingState(false)
                    _sideEffect.send(NotificationSideEffect.Success("수정에 성공하였습니다"))
                }

                is Result.Error -> {
                    setLoadingState(false)
                    _sideEffect.send(NotificationSideEffect.Error(it.throwable))
                }
                is Result.Loading -> {}
            }
        }
    }

    fun delete(id: Long, workspaceId: String) = viewModelScope.launch(dispatcher) {
        setLoadingState(true)
        notificationRepository.deleteNotice(
            notificationId = id,
            workspaceId = workspaceId
        ).collect {
            when (it) {
                is Result.Success -> {
                    setLoadingState(false)
                    _sideEffect.send(NotificationSideEffect.Success("삭제에 성공하였습니다 "))
                }
                is Result.Error -> {
                    setLoadingState(false)
                    _sideEffect.send(NotificationSideEffect.Error(it.throwable))
                }

                is Result.Loading -> {}
            }
        }
    }

    private fun setLoadingState(isLoading: Boolean) = viewModelScope.launch(dispatcher) {
        _state.update {
            it.copy(
                isLoading = isLoading
            )
        }
    }
}