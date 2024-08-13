package com.seugi.workspacedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
): ViewModel() {
    private val _state = MutableStateFlow(WorkspaceDetailUiState())
    val state = _state.asStateFlow()

    fun getMyWorkspace() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    myWorkspace = workspaceRepository.getAllWorkspaces().toImmutableList()
                )
            }
        }
    }
}