package com.seugi.workspacedetail.feature.workspacemember

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.workspacedetail.feature.workspacemember.model.WorkspaceMemberUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkspaceMemberViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(WorkspaceMemberUiState())
    val state = _state.asStateFlow()
    fun getAllMember(
        workspaceId: String
    ) {
        viewModelScope.launch {
            workspaceRepository.getMembers(workspaceId = workspaceId).collect {
                when(it){
                    is Result.Success -> {
                        _state.update {ui ->
                            ui.copy(
                                member = it.data.toImmutableList()
                            )
                        }
                    }
                    is Result.Error -> {

                    }
                    else ->{

                    }
                }
            }
        }
    }
}