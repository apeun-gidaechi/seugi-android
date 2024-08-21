package com.seugi.workspace_create

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.workspace.WorkspaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

@HiltViewModel
class WorkspaceCreateViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository
) : ViewModel() {

    fun createWorkspace(context: Context, workspaceName: String, workspaceImage: Uri?) {
        viewModelScope.launch {
            var file = ""
            if (workspaceImage != null){
                file = uriToFile(context = context, uri = workspaceImage).toString()
            }
            Log.d("TAG", "$workspaceName, $file: ")
            workspaceRepository.createWorkspace(
                workspaceName = workspaceName,
                workspaceImage = file
            ).collect{
                when(it){
                    is Result.Success ->{
                        Log.d("TAG", "성공 ${it.data}: ")
                    }
                    is Result.Error ->{
                        Log.d("TAG", "실패 ${it.throwable}: ")
                    }
                    else ->{

                    }
                }
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
                fileName ?: "temp_image", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}