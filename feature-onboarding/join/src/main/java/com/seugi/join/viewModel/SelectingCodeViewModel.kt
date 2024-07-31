package com.seugi.join.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.join.model.SelectingCodeSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectingCodeViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _selectingCodeSideEffect = Channel<SelectingCodeSideEffect>()
    val selectingCodeSideEffect = _selectingCodeSideEffect.receiveAsFlow()
    fun workspaceApplication(workspaceId: String, workspaceCode: String, role: String) {
        viewModelScope.launch(dispatcher) {
            workspaceRepository.workspaceApplication(
                workspaceId = workspaceId,
                workspaceCode = workspaceCode,
                role = role,
            ).collectLatest {
                when (it) {
                    is Result.Success -> {
                        _selectingCodeSideEffect.send(SelectingCodeSideEffect.SuccessApplication)
                    }
                    is Result.Error -> {
                        _selectingCodeSideEffect.send(SelectingCodeSideEffect.FiledApplication(it.throwable))
                    }
                    is Result.Loading -> {
                    }
                }
            }
        }
    }
}
