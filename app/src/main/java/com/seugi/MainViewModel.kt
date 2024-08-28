package com.seugi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.token.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
    private val tokenRepository: TokenRepository
): ViewModel() {

    private val _state = MutableStateFlow<Boolean?>(null)
    val state = _state.asStateFlow()

    fun loadTokenIsSave() = viewModelScope.launch(dispatcher) {
        tokenRepository.getToken().collect {
            when (it) {
                is Result.Success -> {
                    _state.update { _ ->
                        it.data.accessToken?.isNotEmpty() == true
                    }
                }
                is Result.Error -> {
                    _state.update { _ ->
                        false
                    }
                }
                is Result.Loading -> {}
            }
        }
    }
}