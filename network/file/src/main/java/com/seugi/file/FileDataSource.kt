package com.seugi.file

import com.seugi.network.core.response.BaseResponse

interface FileDataSource {
    suspend fun fileUpload(type: String, file: String): BaseResponse<String>
}
