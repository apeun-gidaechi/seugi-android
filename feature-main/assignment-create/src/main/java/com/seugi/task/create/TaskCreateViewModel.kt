package com.seugi.task.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.assignment.AssignmentRepository
import com.seugi.task.create.model.TaskCreateSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TaskCreateViewModel @Inject constructor(
    private val assignmentRepository: AssignmentRepository,
) : ViewModel() {

    private val _sideEffect = Channel<TaskCreateSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun createTask(workspaceId: String, title: String, description: String, dueDate: LocalDateTime) = viewModelScope.launch {
        assignmentRepository.createTask(
            workspaceId = workspaceId,
            title = title,
            description = description,
            dueDate = dueDate,
        ).collect {
            when (it) {
                is Result.Success -> {
                    _sideEffect.send(TaskCreateSideEffect.Success)
                }
                Result.Loading -> {}
                is Result.Error -> {
                    _sideEffect.send(TaskCreateSideEffect.Failed(it.throwable))
                    it.throwable.printStackTrace()
                }
            }
        }
    }
}
