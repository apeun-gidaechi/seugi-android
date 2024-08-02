package com.seugi.workspace.feature.joinsuccess

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.workspace.feature.joinsuccess.model.JoinSuccessSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class JoinSuccessViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _joinSuccessSideEffect = Channel<JoinSuccessSideEffect>()
    val joinSuccessSideEffect = _joinSuccessSideEffect.receiveAsFlow()
    fun workspaceApplication(role: String) {
        viewModelScope.launch(dispatcher) {
            workspaceRepository.workspaceApplication(
                // TODO 임시 땜방 추후 변경
                workspaceId = "workspaceId",
                workspaceCode = "workspaceCode",
                role = role,
            ).collectLatest {
                when (it) {
                    is Result.Success -> {
                        _joinSuccessSideEffect.send(JoinSuccessSideEffect.SuccessApplication)
                    }
                    is Result.Error -> {
                        _joinSuccessSideEffect.send(JoinSuccessSideEffect.FiledApplication(it.throwable))
                    }
                    is Result.Loading -> {
                    }
                }
            }
        }
    }
}
