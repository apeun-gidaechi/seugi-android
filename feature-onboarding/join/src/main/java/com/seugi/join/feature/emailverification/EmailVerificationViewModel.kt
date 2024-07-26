package com.seugi.join.feature.emailverification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.member.MemberRepository
import com.seugi.join.feature.emailverification.model.EmailVerificationSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class EmailVerificationViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _sideEffect = Channel<EmailVerificationSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun getCode(email: String) = viewModelScope.launch(dispatcher) {
        memberRepository.getCode(email).collectLatest {
            when (it) {
                is Result.Success -> {
                    if (it.data == 200) {
                        _sideEffect.send(EmailVerificationSideEffect.SuccessGetCode)
                    } else {
                        _sideEffect.send(EmailVerificationSideEffect.Error)
                    }
                }

                is Result.Error -> {
                    it.throwable.printStackTrace()
                    _sideEffect.send(EmailVerificationSideEffect.Error)
                }

                is Result.Loading -> {
                }
            }
        }
    }

    fun emailSignUp(name: String, email: String, password: String, code: String) = viewModelScope.launch(dispatcher) {
        memberRepository.emailSignUp(
            name = name,
            email = email,
            password = password,
            code = code,
        ).collectLatest {
            when (it) {
                is Result.Success -> {
                    if (it.data == 200) {
                        _sideEffect.send(EmailVerificationSideEffect.SuccessJoin)
                    } else if (it.data == 400 || it.data == 404 || it.data == 409) {
                        _sideEffect.send(EmailVerificationSideEffect.FiledJoin)
                    } else {
                        _sideEffect.send(EmailVerificationSideEffect.Error)
                    }
                }
                is Result.Error -> {
                    _sideEffect.send(EmailVerificationSideEffect.Error)
                }
                is Result.Loading -> {
                }
            }
        }
    }
}
