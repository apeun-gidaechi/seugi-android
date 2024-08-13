package com.seugi.workspacedetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.workspacedetail.model.WorkspaceDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
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
        workspaceId: String
    ) {
        viewModelScope.launch{
            setLoading(true)
            workspaceRepository.getWorkspaceData(workspaceId).collect{
                when(it){
                    is Result.Success ->{
                        _state.update {uiState ->
                            uiState.copy(
                                nowWorkspace = Pair(it.data.workspaceName, it.data.workspaceId),
                                workspaceImage = it.data.workspaceImageUrl
                            )
                        }
                    }
                    else ->{

                    }
                }
            }
            setLoading(false)
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