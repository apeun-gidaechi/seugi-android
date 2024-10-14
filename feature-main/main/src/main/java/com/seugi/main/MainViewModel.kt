package com.seugi.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.core.model.ProfileModel
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

    fun loadWorkspace() = viewModelScope.launch(dispatcher) {
        launch {
            val localWorkspace = workspaceRepository.getLocalWorkspace()

            Log.d("TAG", "loadWorkspace: $localWorkspace")
            // 중복 로드 방지
            if (localWorkspace != null && localWorkspace.workspaceId == state.value.workspace.workspaceId) return@launch

            if (localWorkspace?.workspaceId?.isNotEmpty() == true) {
                Log.d("TAG", "loadWorkspace: init Local Workspace ${localWorkspace} ")
                _state.update { uiState ->
                    uiState.copy(
                        workspace = localWorkspace
                    )
                }
                loadProfile(localWorkspace.workspaceId)
                return@launch
            }

            Log.d("TAG", "loadWorkspace: called workspace list")
            workspaceRepository.getMyWorkspaces().collect { response ->
                when (response) {
                    is Result.Success -> {
                        val workspaces = response.data
                        var workspaceId: String
                        if (workspaces.isEmpty()) {
                            // 서버에 워크페이스가 없을때 워크페이스 가입
                            _state.update {
                                it.copy(
                                    notJoinWorkspace = true
                                )
                            }
                            return@collect
                        } else {
                            // 워크페이스가 있다면 로컬에 아이디와 비교
                            val localWorkspaceId = localWorkspace?.workspaceId?: ""
                            if (localWorkspaceId.isEmpty()) {
                                // 로컬에 없으면 서버의 처음 워크페이스를 화면에
                                workspaceId = workspaces[0].workspaceId
                                _state.update { mainUi ->
                                    mainUi.copy(
                                        workspace = workspaces[0],
                                    )
                                }
                                workspaceRepository.insertWorkspace(workspaces[0])
                            } else {
                                workspaceId = localWorkspaceId
                                // 로컬에 있다면 로컬이랑 같은 아이디의 워크페이스를 화면에
                                workspaces.forEach {
                                    if (workspaceId == it.workspaceId) {
                                        _state.update { mainUi ->
                                            mainUi.copy(
                                                workspace = it,
                                            )
                                        }
                                        workspaceRepository.insertWorkspace(it)
                                    }
                                }
                            }
                        }
                        // 기존 로직
                        loadProfile(workspaces[0].workspaceId)
                    }

                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                notJoinWorkspace = true
                            )
                        }
                        response.throwable.printStackTrace()
                    }

                    Result.Loading -> {}
                }
            }
        }
    }

    fun loadLocalWorkspace() {
        viewModelScope.launch {
            val workspace = workspaceRepository.getLocalWorkspace()
            _state.update {
                it.copy(
                    profile = it.profile.copy(
                        workspaceId = workspace?.workspaceId?: "",
                    ),
                    workspace = workspace?: it.workspace
                )
            }
            loadProfile(workspace?.workspaceId?: "")
        }
    }

    private fun loadProfile(workspaceId: String) {
        viewModelScope.launch(dispatcher) {
            profileRepository.getProfile(workspaceId).collect {
                when (it) {
                    is Result.Success -> {
                        _state.update { state ->
                            state.copy(
                                userId = it.data.member.id,
                                profile = it.data,
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    fun setProfileModel(profileModel: ProfileModel) = viewModelScope.launch(dispatcher) {
        _state.update {
            it.copy(
                profile = profileModel,
            )
        }
    }
}
