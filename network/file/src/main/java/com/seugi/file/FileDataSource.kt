package com.seugi.file

import com.seugi.file.response.FileResponse
import com.seugi.network.core.response.BaseResponse
import java.io.InputStream

interface FileDataSource {
    suspend fun fileUpload(type: String, file: String): BaseResponse<FileResponse>

    suspend fun fileUpload(type: String, fileName: String, fileMimeType: String, byteArray: ByteArray): BaseResponse<FileResponse>

    suspend fun fileUpload(type: String, fileName: String, fileMimeType: String, fileInputStream: InputStream): BaseResponse<FileResponse>
}
