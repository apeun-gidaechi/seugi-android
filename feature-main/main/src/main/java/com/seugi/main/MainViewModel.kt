package com.seugi.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.profile.ProfileRepository
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.main.model.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val workspaceRepository: WorkspaceRepository,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState())
    val state = _state.asStateFlow()

    fun loadWorkspaceId() = viewModelScope.launch(dispatcher) {
        launch {
            val localWorkspaceId = workspaceRepository.getWorkspaceId()
            workspaceRepository.getMyWorkspaces().collect { response ->
                when (response) {
                    is Result.Success -> {
                        val workspaces = response.data
                        val workspaceId = when {
                            workspaces.isEmpty() -> ""
                            localWorkspaceId.isEmpty() -> {
                                // 로컬에 아이디가 없으면 서버의 첫 번째 워크스페이스 ID 사용
                                workspaces.first().workspaceId
                            }
                            else -> {
                                // 로컬에 아이디가 있으면 그대로 사용
                                localWorkspaceId
                            }
                        }
                        // 상태 업데이트 및 데이터 로드
                        _state.update {
                            it.copy(workspaceId = workspaceId)
                        }
                        loadData(workspaceId = workspaceId)
                    }

                    is Result.Error -> {
                        response.throwable.printStackTrace()
                    }

                    Result.Loading -> {}
                }
            }
        }
    }

    fun loadLocalWorkspaceId() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    workspaceId = workspaceRepository.getWorkspaceId(),
                )
            }
        }
    }

    private fun loadData(workspaceId: String) {
        viewModelScope.launch(dispatcher) {
            profileRepository.getProfile(workspaceId).collect {
                when (it) {
                    is Result.Success -> {
                        _state.update { state ->
                            state.copy(
                                userId = it.data.member.id,
                                myProfile = it.data
                            )
                        }
                    }

                    else -> {}
                }
            }

            workspaceRepository.getPermission(workspaceId).collect {
                when (it) {
                    is Result.Success -> {
                        _state.update { state ->
                            state.copy(
                                permission = it.data,
                            )
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    fun setWorkspaceId(workspaceId: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                workspaceId = workspaceId,
            )
        }
    }
}
