package com.seugi.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.member.MemberRepository
import com.seugi.data.token.TokenRepository
import com.seugi.login.model.EmailSignInSideEffect
import com.seugi.login.model.EmailSignInState
import com.seugi.network.request.EmailSignInRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class EmailSignInViewModel @Inject constructor(
    private val emailSignInRepository: MemberRepository,
    private val tokenRepository: TokenRepository,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow(EmailSignInState())
    val state = _state.asStateFlow()

    private val _emailSignInSideEffect = Channel<EmailSignInSideEffect>()
    val emailSignInSideEffect = _emailSignInSideEffect.receiveAsFlow()

    fun emailSignIn(email: String, password: String) {
        viewModelScope.launch(dispatcher) {
            emailSignInRepository.emailSignIn(
                body = EmailSignInRequest(
                    email = email,
                    password = password,
                ),
            ).collect {
                when (it) {
                    is Result.Success -> {
                        _emailSignInSideEffect.send(
                            EmailSignInSideEffect.SuccessLogin,
                        )
                        val accessToken = it.data.accessToken
                        val refreshToken = it.data.refreshToken
                        tokenRepository.insertToken(
                            accessToken = accessToken,
                            refreshToken = refreshToken,
                        )
                    }

                    is Result.Error -> {
                        _emailSignInSideEffect.send(
                            EmailSignInSideEffect.FailedLogin(
                                it.throwable,
                            ),
                        )
                    }

                    is Result.Loading -> {}
                }
            }
        }
    }
}
