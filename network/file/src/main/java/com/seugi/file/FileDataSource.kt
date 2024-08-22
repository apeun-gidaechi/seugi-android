package com.seugi.file

import com.seugi.file.request.FileType
import com.seugi.network.core.response.BaseResponse

interface FileDataSource {
    suspend fun fileUpload(type: FileType, file: String): BaseResponse<String>
}
