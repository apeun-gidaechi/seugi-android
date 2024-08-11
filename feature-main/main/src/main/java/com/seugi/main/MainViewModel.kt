package com.seugi.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.profile.ProfileRepository
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.data.workspace.mapper.toEntities
import com.seugi.data.workspace.mapper.toEntity
import com.seugi.data.workspace.model.WorkspaceModel
import com.seugi.local.room.dao.WorkspaceDao
import com.seugi.local.room.model.WorkspaceEntity
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
        workspaceRepository.getMyWorkspaces().collect {
            when(it){
                is Result.Success -> {
                    val dummy: List<WorkspaceModel> = listOf(WorkspaceModel("0", "0", "0", 0, teacher = listOf(0), student = listOf(0), middleAdmin = listOf(0)),WorkspaceModel("1", "1", "1", 1, teacher = listOf(1), student = listOf(1), middleAdmin = listOf(1)))
                    val workspaces = it.data.toEntities()
                    workspaceDao.insert(workspaces)
                    Log.d("TAG", "워크페이스 ${it.data} ")
                }
                else ->{

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
