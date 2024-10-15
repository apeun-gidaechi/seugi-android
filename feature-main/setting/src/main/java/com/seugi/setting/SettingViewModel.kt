package com.seugi.setting

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.common.utiles.combineWhenAllComplete
import com.seugi.data.file.FileRepository
import com.seugi.data.file.model.FileType
import com.seugi.data.member.MemberRepository
import com.seugi.data.token.TokenRepository
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.setting.model.SettingSideEffect
import com.seugi.setting.model.SettingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
    private val tokenRepository: TokenRepository,
    private val memberRepository: MemberRepository,
    private val fileRepository: FileRepository,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _sideEffect = Channel<SettingSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _state = MutableStateFlow(SettingUiState())
    val state = _state.asStateFlow()

    fun logout() = viewModelScope.launch(dispatcher) {
        combineWhenAllComplete(
            workspaceRepository.deleteWorkspace(),
            tokenRepository.deleteToken(),
        ) { _, _ ->
            return@combineWhenAllComplete true
        }.collect {
            _sideEffect.send(SettingSideEffect.SuccessLogout)
        }
    }

    fun withdraw() = viewModelScope.launch(dispatcher) {
        _state.update {
            it.copy(
                isLoading = true,
            )
        }
        memberRepository.remove().collect {
            when (it) {
                is Result.Success -> {
                    combineWhenAllComplete(
                        workspaceRepository.deleteWorkspace(),
                        tokenRepository.deleteToken(),
                    ) { _, _ ->
                        return@combineWhenAllComplete true
                    }.collect {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                            )
                        }
                        _sideEffect.send(SettingSideEffect.SuccessWithdraw)
                    }
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    it.throwable.printStackTrace()
                    _state.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                    _sideEffect.send(SettingSideEffect.FailedWithdraw(it.throwable))
                }
            }
        }
    }

    private fun editProfile(name: String, profileImage: String, birth: String) {
        viewModelScope.launch {
            memberRepository.editProfile(
                name = name,
                picture = profileImage,
                birth = birth,
            ).collect {
                when (it) {
                    is Result.Error -> {
                        it.throwable.printStackTrace()
                    }
                    is Result.Loading -> {}
                    is Result.Success -> {
                        _sideEffect.send(SettingSideEffect.SuccessEdit)
                        Log.d("TAG", "editProfile: ${it.data}")
                    }
                }
            }
        }
    }

    fun fileUpload(name: String,fileByteArray: ByteArray, fileMimeType: String, fileName: String, fileByte: Long) {
        viewModelScope.launch {
            fileRepository.fileUpload(
                type = FileType.FILE,
                fileName = fileName,
                fileMimeType = fileMimeType,
                fileByteArray = fileByteArray,
            ).collect {
                    when (it) {
                        is Result.Success -> {
                            editProfile(name, it.data.url, "")
                        }

                        is Result.Error -> {
                            it.throwable.printStackTrace()
                        }

                        else -> {
                        }
                    }
                }
        }
    }
}
