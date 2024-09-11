package com.seugi.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.common.utiles.combineWhenAllComplete
import com.seugi.data.token.TokenRepository
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.setting.model.SettingSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
    private val tokenRepository: TokenRepository,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _sideEffect = Channel<SettingSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()


    fun logout() = viewModelScope.launch(dispatcher) {
        combineWhenAllComplete(
            workspaceRepository.deleteWorkspace(),
            tokenRepository.deleteToken()
        ) { _, _ ->
           return@combineWhenAllComplete true
        }.collect {
            _sideEffect.send(SettingSideEffect.SuccessLogout)
        }
    }
}