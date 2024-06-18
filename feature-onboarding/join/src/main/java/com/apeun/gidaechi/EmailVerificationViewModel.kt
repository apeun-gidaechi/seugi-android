package com.apeun.gidaechi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.common.utiles.DispatcherType
import com.apeun.gidaechi.common.utiles.SeugiDispatcher
import com.apeun.gidaechi.data.MemberRepository
import com.apeun.gidaechi.model.EmailVerificationSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailVerificationViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _sideEffect = Channel<EmailVerificationSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun getCode(email: String) = viewModelScope.launch(dispatcher) {
        memberRepository.getCode(email).collectLatest {
            when (it) {
                is Result.Success -> {
                    if (it.data == "전송 성공 !") {
                        _sideEffect.send(EmailVerificationSideEffect.SuccessGetCode)
                    } else {
                        _sideEffect.send(EmailVerificationSideEffect.Error)
                    }
                }

                is Result.Error -> {
                    _sideEffect.send(EmailVerificationSideEffect.Error)
                }

                Result.Loading -> {

                }
            }
        }
    }
}