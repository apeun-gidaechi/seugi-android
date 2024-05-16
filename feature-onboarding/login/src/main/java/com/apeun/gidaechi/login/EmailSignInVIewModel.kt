package com.apeun.gidaechi.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.data.repository.EmailSignInRepositoryImpl
import com.apeun.gidaechi.login.model.TokenModel
import com.apeun.gidaechi.network.request.EmailSignInRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailSignInVIewModel @Inject constructor(
    private val emailSignInRepositoryImpl: EmailSignInRepositoryImpl
) : ViewModel() {


    private val _state = MutableStateFlow(TokenModel())
    val state = _state.asStateFlow()


    fun emailSignIn(email: String, password: String) {
        viewModelScope.launch {
            emailSignInRepositoryImpl.emailSignIn(
                body = EmailSignInRequest(
                    email = email,
                    password = password
                )
            ).collect {
                when (it) {
                    is Result.Success -> {
                        val accessToken = it.data.accessToken
                        val refreshToken = it.data.refreshToken
                        _state.value = _state.value.copy(
                            accessToken = accessToken,
                            refreshToken = refreshToken
                        )
                    }

                    is Result.Error -> {
                        Log.d("TAG", "Error: ${it.throwable.message}")
                    }
                    is Result.Loading -> {}
                }
            }
        }
    }
}