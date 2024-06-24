package com.apeun.gidaechi.viewModel

import android.util.Log
import androidx.compose.ui.semantics.Role
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.common.utiles.DispatcherType
import com.apeun.gidaechi.common.utiles.SeugiDispatcher
import com.apeun.gidaechi.data.workspace.WorkspaceRepository
import com.apeun.gidaechi.model.SelectingCodeSideEffect
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
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _selectingCodeSideEffect = Channel<SelectingCodeSideEffect>()
    val selectingCodeSideEffect = _selectingCodeSideEffect.receiveAsFlow()
    fun workspaceApplication(
        workspaceId: String,
        workspaceCode: String,
        role: String
    ){
        viewModelScope.launch(dispatcher) {
            workspaceRepository.workspaceApplication(
                workspaceId = workspaceId,
                workspaceCode = workspaceCode,
                role = role
            ).collectLatest {
                when(it){
                    is Result.Success->{
                        Log.d("TAG", "성공:${it.data} ")
                        _selectingCodeSideEffect.send(SelectingCodeSideEffect.SuccessApplication)
                    }
                    is Result.Error ->{
                        Log.d("TAG", "실패:${it.throwable} ")
                        _selectingCodeSideEffect.send(SelectingCodeSideEffect.FiledApplication(it.throwable))

                    }
                    is Result.Loading ->{

                    }
                }
            }
        }
    }
}