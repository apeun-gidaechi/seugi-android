package com.apeun.gidaechi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.data.MemberRepository
import com.apeun.gidaechi.model.EmailSignUpSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class EmailVerificationViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
) : ViewModel() {

    private val _emailSignUpSideEffect = Channel<EmailSignUpSideEffect>()
    val emailSignUpSideEffect = _emailSignUpSideEffect.receiveAsFlow()

    fun emailSignUp(name: String, email: String, password: String, code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            memberRepository.emailSignUp(
                name = name,
                email = email,
                password = password,
                code = code,
            ).collect {
                when (it) {
                    is Result.Success -> {
                        Log.d("TAG", "emailSignUp: ${it.data}")
                        if (it.data == "코드가 일치하지 않아요") {
                            _emailSignUpSideEffect.send(EmailSignUpSideEffect.FailedVeritication)
                        } else {
                            _emailSignUpSideEffect.send(EmailSignUpSideEffect.Success)
                        }
                    }

                    is Result.Error -> {
                        _emailSignUpSideEffect.send(EmailSignUpSideEffect.FailedJoin)
                    }

                    Result.Loading -> {
                    }
                }
            }
        }
    }

    fun getCode(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            memberRepository.getCode(email).collectLatest {
                when (it) {
                    is Result.Success -> {
                        Log.d("TAG", "성공: ${it.data}")
                        _emailSignUpSideEffect.send(EmailSignUpSideEffect.SuccessGetCode)
                    }

                    is Result.Error -> {
                        Log.d("TAG", "실패: ${it.throwable}")
                        _emailSignUpSideEffect.send(EmailSignUpSideEffect.FailedGetCode)
                    }

                    Result.Loading -> {
                    }
                }
            }
        }
    }
}
