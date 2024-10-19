package com.seugi.workspacedetail.feature.invitemember

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seugi.common.model.Result
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.workspacedetail.feature.invitemember.model.WaitMemberUiState
import com.seugi.workspacedetail.feature.workspacemember.model.WorkspaceMemberUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InviteMemberViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository
): ViewModel() {

    private val _state = MutableStateFlow(WaitMemberUiState())
    val state = _state.asStateFlow()

    fun getWaitMembers(
        workspaceId: String,
        role: String
    ) {
        viewModelScope.launch{
            workspaceRepository.getWaitMembers(
                workspaceId = workspaceId,
                role = role
            ).collect{
                when(it){
                    is Result.Success -> {
                        if (role == "STUDENT"){
                            _state.update { ui ->
                                ui.copy(
                                    student = it.data.toImmutableList()
                                )
                            }
                        }else if(role == "TEACHER"){
                            _state.update { ui ->
                                ui.copy(
                                    teacher = it.data.toImmutableList()
                                )
                            }
                        }
                    }
                    is Result.Error -> {}
                    is Result.Loading -> {}
                }
            }
        }
    }
}