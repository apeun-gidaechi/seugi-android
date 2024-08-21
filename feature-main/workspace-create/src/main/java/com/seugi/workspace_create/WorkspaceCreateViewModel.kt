package com.seugi.workspace_create

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.data.workspace.WorkspaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class WorkspaceCreateViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository
) : ViewModel() {

    fun createWorkspace(context: Context, workspaceName: String, workspaceImage: Uri) {
        viewModelScope.launch {
            val file = uriToFile(context = context, workspaceImage) ?: ""

            workspaceRepository.createWorkspace(
                workspaceName = workspaceName,
                workspaceImage = file.toString()
            )
        }
    }


    private fun uriToFile(context: Context, uri: Uri): File? {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val columnIndex = it.getColumnIndex("_data")
            if (columnIndex != -1 && it.moveToFirst()) {
                val filePath = it.getString(columnIndex)
                return File(filePath)
            }
        }
        return null
    }

}