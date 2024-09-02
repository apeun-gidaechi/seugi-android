package com.seugi.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.profile.ProfileRepository
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.home.model.CommonUiState
import com.seugi.main.model.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
            val localWorkspaceId = workspaceRepository.getWorkspaceId()
            workspaceRepository.getMyWorkspaces().collect { response ->
                when (response) {
                    is Result.Success -> {
                        val workspaces = response.data
                        var workspaceId = ""
                        if (workspaces.isEmpty()) {
                            // 서버에 워크페이스가 없을때 워크페이스 가입
                        } else {
                            // 워크페이스가 있다면 로컬에 아이디와 비교
                            workspaceId = if (localWorkspaceId.isEmpty()) {
                                // 로컬에 없으면 서버의 처음 워크페이스를 화면에
                                workspaces[0].workspaceId
                            } else {
                                // 로컬에 있다면 유지
                                localWorkspaceId
                            }
                        }
                        _state.update {
                            it.copy(
                                workspaceId = workspaceId
                            )
                        }
                        loadData(workspaceId = workspaceId)
                    }

                    is Result.Error -> {}

                    Result.Loading -> {}
                }
            }
        }
    }

    private fun loadData(
        workspaceId: String
    ){
        viewModelScope.launch{
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
