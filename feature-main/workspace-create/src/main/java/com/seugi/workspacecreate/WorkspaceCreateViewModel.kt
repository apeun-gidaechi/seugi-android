package com.seugi.workspacecreate

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.file.FileRepository
import com.seugi.data.file.model.FileType
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.workspacecreate.model.WorkspaceCreateSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class WorkspaceCreateViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
    private val fileRepository: FileRepository,
) : ViewModel() {

    private val _sideEffect = Channel<WorkspaceCreateSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun createWorkspace(workspaceName: String, workspaceImage: String) {
        viewModelScope.launch {
            workspaceRepository.createWorkspace(
                workspaceName = workspaceName,
                workspaceImage = workspaceImage,
            ).collect {
                when (it) {
                    is Result.Success -> {
                        _sideEffect.send(WorkspaceCreateSideEffect.SuccessCreate)
                    }
                    is Result.Error -> {
                        it.throwable.printStackTrace()
                        _sideEffect.send(WorkspaceCreateSideEffect.Error(it.throwable))
                    }
                    else -> {
                    }
                }
            }
        }
    }

    fun fileUpload(workspaceName: String,fileByteArray: ByteArray, fileMimeType: String, fileName: String, fileByte: Long) {
        viewModelScope.launch {
            fileRepository.fileUpload(
                type = FileType.FILE,
                fileName = fileName,
                fileMimeType = fileMimeType,
                fileByteArray = fileByteArray,
            ).collect {
                when (it) {
                    is Result.Success -> {
                        createWorkspace(workspaceName, it.data.url)
                    }

                    is Result.Error -> {
                        it.throwable.printStackTrace()
                    }

                    else -> {
                    }
                }
            }
        }
    }
}
