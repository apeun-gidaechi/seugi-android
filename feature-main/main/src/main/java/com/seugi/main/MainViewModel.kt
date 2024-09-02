package com.seugi.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.profile.ProfileRepository
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.main.model.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val workspaceRepository: WorkspaceRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState())
    val state = _state.asStateFlow()

    fun loadWorkspaceId() = viewModelScope.launch {
        launch {
            val workspaceId = when {
                workspaces.isEmpty() -> {
                    // 서버에 워크스페이스가 없을 때 처리
                    return@collect // 이후 로직을 실행하지 않음
                }
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
        }
    }

    private fun loadData(workspaceId: String) {
        viewModelScope.launch {
            profileRepository.getProfile(workspaceId).collect {
                when (it) {
                    is Result.Success -> {
                        _state.update { state ->
                            state.copy(
                                userId = it.data.member.id,
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
