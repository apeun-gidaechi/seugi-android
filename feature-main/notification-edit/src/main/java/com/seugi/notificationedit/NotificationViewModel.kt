package com.seugi.notificationedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.notificationedit.model.NotificationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(NotificationUiState())
    val state = _state.asStateFlow()

    fun load(id: Long) = viewModelScope.launch {
        _state.update {
            it.copy(
                title = "테스트",
                content = "테스트 내용입니다."
            )
        }
    }

    fun setTitle(text: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                title = text
            )
        }
    }

    fun setContent(text: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                content = text
            )
        }
    }
}