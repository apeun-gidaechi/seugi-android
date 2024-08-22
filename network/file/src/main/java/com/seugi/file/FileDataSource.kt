package com.seugi.file

import com.seugi.file.request.FileType
import com.seugi.file.response.FileResponse
import com.seugi.network.core.response.BaseResponse
import io.ktor.http.ContentType

interface FileDataSource {
    suspend fun fileUpload(type: FileType, file: String):BaseResponse<FileResponse>
}