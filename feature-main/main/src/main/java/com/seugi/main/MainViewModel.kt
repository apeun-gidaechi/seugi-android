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

    fun load() = viewModelScope.launch {
        launch {
            profileRepository.getProfile(_state.value.workspaceId).collect {
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
        }
        launch {
            workspaceRepository.getPermission(_state.value.workspaceId).collect {
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
