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

    private fun createWorkspace(workspaceName: String, workspaceImage: String) {
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

    fun fileUpload(context: Context, workspaceUri: Uri?, workspaceName: String) {
        viewModelScope.launch {
            val file: String
            if (workspaceUri != null) {
                file = uriToFile(context = context, uri = workspaceUri).toString()
                fileRepository.fileUpload(file = file, type = FileType.IMG).collect {
                    when (it) {
                        is Result.Success -> {
                            createWorkspace(
                                workspaceName = workspaceName,
                                workspaceImage = it.data,
                            )
                        }
                        is Result.Error -> {
                            it.throwable.printStackTrace()
                            _sideEffect.send(WorkspaceCreateSideEffect.Error(it.throwable))
                        }
                        else -> {
                        }
                    }
                }
            } else {
                createWorkspace(
                    workspaceName = workspaceName,
                    workspaceImage = "",
                )
            }
        }
    }

    private fun uriToFile(context: Context, uri: Uri): File? {
        val contentResolver: ContentResolver = context.contentResolver
        val file = createTempFile(context, uri)

        file?.let {
            try {
                val inputStream: InputStream? = contentResolver.openInputStream(uri)
                val outputStream: OutputStream = FileOutputStream(file)
                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }
        return file
    }

    private fun createTempFile(context: Context, uri: Uri): File? {
        val fileName = uri.lastPathSegment?.split("/")?.lastOrNull()
        return try {
            val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            File.createTempFile(
                fileName ?: "temp_image",
                ".jpg",
                storageDir,
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
