package com.seugi.data.file

import com.seugi.common.model.Result
import com.seugi.data.file.model.FileType
import com.seugi.network.core.response.BaseResponse
import kotlinx.coroutines.flow.Flow

interface FileRepository {

    suspend fun fileUpload(file: String, type: FileType): Flow<Result<String>>

    suspend fun fileUpload(type: FileType, fileName: String, byteArray: ByteArray,): Flow<Result<String>>
}
