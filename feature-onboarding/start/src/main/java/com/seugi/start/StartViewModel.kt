package com.seugi.start

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.firebasetoken.FirebaseTokenRepository
import com.seugi.data.oauth.OauthRepository
import com.seugi.data.token.TokenRepository
import com.seugi.start.model.LoginState
import com.seugi.start.model.StartUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class StartViewModel @Inject constructor(
    private val oauthRepository: OauthRepository,
    private val tokenRepository: TokenRepository,
    private val firebaseTokenRepository: FirebaseTokenRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(StartUiState())
    val state = _state.asStateFlow()

    private fun googleLogin(code: String, fcmToken: String) {
        viewModelScope.launch {
            oauthRepository.authenticate(
                code = code,
                fcmToken = fcmToken,
            ).collect {
                when (it) {
                    is Result.Success -> {
                        insertToken(
                            accessToken = it.data.accessToken,
                            refreshToken = it.data.refreshToken,
                        )
                    }
                    is Result.Error -> {
                        it.throwable.printStackTrace()
                        _state.update { ui ->
                            ui.copy(
                                loginState = LoginState.Error,
                            )
                        }
                    }
                    is Result.Loading -> {}
                }
            }
        }
    }

    private fun insertToken(accessToken: String, refreshToken: String) {
        viewModelScope.launch {
            tokenRepository.insertToken(
                accessToken = accessToken,
                refreshToken = refreshToken,
            )
        }
        _state.update { ui ->
            ui.copy(
                loginState = LoginState.Success,
            )
        }
    }

    fun getFcmToken(code: String) {
        viewModelScope.launch {
            firebaseTokenRepository.getToken().collect {
                when (it) {
                    is Result.Error -> {
                        it.throwable.printStackTrace()
                    }
                    Result.Loading -> {
                        _state.update { ui ->
                            ui.copy(
                                loginState = LoginState.Loading,
                            )
                        }
                    }
                    is Result.Success -> {
                        val fcmToken = it.data.firebaseToken ?: ""
                        Log.d("TAG", "getFcmToken: $fcmToken")
                        googleLogin(code, fcmToken)
                    }
                }
            }
        }
    }
}
