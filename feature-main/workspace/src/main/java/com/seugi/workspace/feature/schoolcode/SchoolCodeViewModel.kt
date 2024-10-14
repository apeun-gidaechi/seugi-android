package com.seugi.workspace.feature.schoolcode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.workspace.feature.schoolcode.model.SchoolCodeModel
import com.seugi.workspace.feature.schoolcode.model.SchoolCodeSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SchoolCodeViewModel @Inject constructor(
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
    private val workspaceRepository: WorkspaceRepository,
) : ViewModel() {

    private val _schoolCodeModel = MutableStateFlow(SchoolCodeModel())
    val schoolCodeModel = _schoolCodeModel.asStateFlow()

    private val _schoolCodeSideEffect = Channel<SchoolCodeSideEffect>()
    val schoolCodeSideEffect = _schoolCodeSideEffect.receiveAsFlow()

    fun checkWorkspace(schoolCode: String) {
        viewModelScope.launch(dispatcher) {
            workspaceRepository.checkWorkspace(schoolCode).collectLatest {
                when (it) {
                    is Result.Success -> {
                        val data = it.data
                        _schoolCodeModel.value = _schoolCodeModel.value.copy(
                            workspaceId = data.workspaceId,
                            workspaceName = data.workspaceName,
                            workspaceImageUrl = data.workspaceImageUrl,
                            studentCount = data.studentCount,
                            teacherCount = data.teacherCount,
                        )
                        _schoolCodeSideEffect.send(SchoolCodeSideEffect.SuccessSearchWorkspace)
                    }

                    is Result.Error -> {
                        it.throwable.printStackTrace()
                        _schoolCodeSideEffect.send(SchoolCodeSideEffect.FiledSearchWorkspace(it.throwable))
                    }

                    is Result.Loading -> {}
                }
            }
        }
    }
}
