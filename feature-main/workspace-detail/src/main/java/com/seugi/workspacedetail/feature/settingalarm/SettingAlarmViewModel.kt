package com.seugi.workspacedetail.feature.settingalarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.workspace.WorkspaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingAlarmViewModel @Inject constructor(
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
    private val workspaceRepository: WorkspaceRepository
): ViewModel() {

    private val _alarmState = MutableStateFlow(false)
    val alarmState = _alarmState.asStateFlow()


    fun load(workspaceId: String) = viewModelScope.launch(dispatcher) {
        workspaceRepository.getIsWorkspaceReceiveFCM(workspaceId).collect {
            when (it) {
                is Result.Success -> {
                    _alarmState.update {  _ ->
                        it.data
                    }
                }
                is Result.Error -> {
                    it.throwable.printStackTrace()
                }
                Result.Loading -> {}
            }
        }
    }

    fun changeAlarmState(alarmState: Boolean, workspaceId: String) = viewModelScope.launch(dispatcher) {
        workspaceRepository.changeIsWorkspaceReceiveFCM(alarmState, workspaceId).collect {
            when (it) {
                is Result.Success -> {
                    _alarmState.update {  _ ->
                        alarmState
                    }
                }
                is Result.Error -> {
                    it.throwable.printStackTrace()
                }
                Result.Loading -> {}
            }
        }
    }

}