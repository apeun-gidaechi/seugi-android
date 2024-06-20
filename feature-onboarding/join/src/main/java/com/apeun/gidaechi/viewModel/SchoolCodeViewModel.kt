package com.apeun.gidaechi.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.common.utiles.DispatcherType
import com.apeun.gidaechi.common.utiles.SeugiDispatcher
import com.apeun.gidaechi.data.workspace.WorkspaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolCodeViewModel @Inject constructor(
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
    private val workspaceRepository: WorkspaceRepository
): ViewModel() {

    fun checkWorkspace(schoolCode: String){
        viewModelScope.launch(dispatcher) {
            workspaceRepository.checkWorkspace(schoolCode).collectLatest {
                when(it){
                    is Result.Success ->{
                        Log.d("TAG", "성공: ${it.data}")
                    }
                    is Result.Error ->{
                        Log.d("TAG", "실패: ${it.throwable}")

                    }
                    is Result.Loading ->{}
                }
            }
        }
    }
}