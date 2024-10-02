package com.seugi.start

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.oauth.OauthRepository
import com.seugi.data.token.TokenRepository
import com.seugi.login.model.EmailSignInSideEffect
import com.seugi.start.model.StartSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val oauthRepository: OauthRepository,
    private val tokenRepository: TokenRepository
): ViewModel() {

    private val _startSideEffect = Channel<StartSideEffect>()
    val startSideEffect = _startSideEffect.receiveAsFlow()

    fun googleLogin(code: String){
        viewModelScope.launch {
            oauthRepository.authenticate(code = code).collect{
                when(it){
                    is Result.Success -> {
                        insertToken(
                            accessToken = it.data.accessToken,
                            refreshToken = it.data.refreshToken
                        )
                        _startSideEffect.send(StartSideEffect.SuccessLogin)
                    }
                    is Result.Error -> {
                        it.throwable.printStackTrace()
                    }
                    is Result.Loading -> {}
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
    }


}