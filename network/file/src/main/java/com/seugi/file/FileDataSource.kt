package com.seugi.file

import com.seugi.common.model.Result
import com.seugi.file.response.FileResponse
import com.seugi.network.core.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import java.io.InputStream

interface FileDataSource {
    suspend fun fileUpload(type: String, file: String): BaseResponse<FileResponse>

    suspend fun fileUpload(type: String, fileName: String, fileMimeType: String, byteArray: ByteArray,): BaseResponse<FileResponse>

    suspend fun fileUpload(type: String, fileName: String, fileMimeType: String, fileInputStream: InputStream): BaseResponse<FileResponse>
}
