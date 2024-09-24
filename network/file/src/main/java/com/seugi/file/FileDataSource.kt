package com.seugi.file

import com.seugi.network.core.response.BaseResponse

interface FileDataSource {
    suspend fun fileUpload(type: String, file: String): BaseResponse<String>

    suspend fun fileUpload(type: String, fileName: String, byteArray: ByteArray,): BaseResponse<String>
}
