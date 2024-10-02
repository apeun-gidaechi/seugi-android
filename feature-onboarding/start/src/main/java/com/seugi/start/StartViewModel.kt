package com.seugi.start

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.oauth.OauthRepository
import com.seugi.data.token.TokenRepository
import com.seugi.login.model.EmailSignInSideEffect
import com.seugi.start.model.LoginState
import com.seugi.start.model.StartUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val oauthRepository: OauthRepository,
    private val tokenRepository: TokenRepository
): ViewModel() {

    private val _state = MutableStateFlow(StartUiState())
    val state = _state.asStateFlow()

    fun googleLogin(code: String){
        viewModelScope.launch {
            oauthRepository.authenticate(code = code).collect{
                when(it){
                    is Result.Success -> {
                        insertToken(
                            accessToken = it.data.accessToken,
                            refreshToken = it.data.refreshToken
                        )

                    }
                    is Result.Error -> {
                        it.throwable.printStackTrace()
                        _state.update {ui ->
                            ui.copy(
                                loginState = LoginState.Error
                            )
                        }
                    }
                    is Result.Loading -> {
                        _state.update {ui ->
                            ui.copy(
                                loginState = LoginState.Loading
                            )
                        }
                    }
                }
            }
        }
    }

    private fun insertToken(
        accessToken: String,
        refreshToken: String
    ){
        viewModelScope.launch {
            tokenRepository.insertToken(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }
        _state.update {ui ->
            ui.copy(
                loginState = LoginState.Success
            )
        }
    }

}