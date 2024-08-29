package com.seugi.workspacedetail.feature.workspacedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.data.workspace.model.WorkspaceModel
import com.seugi.workspacedetail.feature.workspacedetail.model.WorkspaceDetailSideEffect
import com.seugi.workspacedetail.feature.workspacedetail.model.WorkspaceDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class WorkspaceDetailViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(WorkspaceDetailUiState())
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<WorkspaceDetailSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun loadWorkspace() {
        viewModelScope.launch {
            workspaceRepository.getMyWorkspaces().collect {
                when (it) {
                    is Result.Success -> {
                        _state.update { uiState ->
                            uiState.copy(
                                myWorkspace = it.data.toImmutableList(),
                            )
                        }
                    }
                    is Result.Error -> {
                        it.throwable.printStackTrace()
                        _sideEffect.send(WorkspaceDetailSideEffect.Error(it.throwable))
                    }
                    else -> {
                    }
                }
            }
            workspaceRepository.getWaitWorkspaces().collect {
                when (it) {
                    is Result.Success -> {
                        _state.update { uiState ->
                            uiState.copy(
                                waitWorkspace = it.data.toImmutableList(),
                            )
                        }
                    }
                    is Result.Error -> {
                        it.throwable.printStackTrace()
                        _sideEffect.send(WorkspaceDetailSideEffect.Error(it.throwable))
                    }
                    else -> {
                    }
                }
            }
        }
    }
    fun changeNowWorkspace(workspaceId: String) {
        viewModelScope.launch {
            setLoading(true)
            workspaceRepository.getWorkspaceData(workspaceId).collect {
                when (it) {
                    is Result.Success -> {
                        _state.update { uiState ->
                            uiState.copy(
                                nowWorkspace = it.data
                            )
                        }
                    }
                    is Result.Error -> {
                        it.throwable.printStackTrace()
                        _sideEffect.send(WorkspaceDetailSideEffect.Error(it.throwable))
                    }
                    else -> {
                    }
                }
            }
            workspaceRepository.addWorkspaceId(workspaceId)
            setLoading(false)
        }
    }

    fun setLoading(isLoading: Boolean) {
        _state.update {
            it.copy(
                loading = isLoading,
            )
        }
    }
}
