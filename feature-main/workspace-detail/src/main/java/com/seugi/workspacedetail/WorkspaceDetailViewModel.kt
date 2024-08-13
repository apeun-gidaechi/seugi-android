package com.seugi.workspacedetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.workspacedetail.model.WorkspaceDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkspaceDetailViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository
) : ViewModel() {
    private val _state = MutableStateFlow(WorkspaceDetailUiState())
    val state = _state.asStateFlow()


    fun loadWorkspace() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    myWorkspace = workspaceRepository.getAllWorkspaces().toImmutableList()
                )
            }
            workspaceRepository.getWaitWorkspaces().collect {
                when (it) {
                    is Result.Success -> {
                        Log.d("TAG", "위에 ${it.data}: ")
                        _state.update { uiState ->
                            uiState.copy(
                                waitWorkspace = it.data.toImmutableList()
                            )
                        }

                    }

                    else -> {

                    }
                }
            }
        }
    }
    fun changeNowWorkspace(
        workspaceName: String,
        workspaceId: String
    ) {
        _state.update {
            it.copy(
                nowWorkspace = Pair(workspaceName, workspaceId)
            )
        }
    }

    fun setLoading(isLoading: Boolean) {
        _state.update {
            it.copy(
                loading = isLoading
            )
        }
    }
}