package com.seugi.data.file

import com.seugi.common.model.Result
import com.seugi.file.request.FileType
import kotlinx.coroutines.flow.Flow

interface FileRepository {

    suspend fun fileUpload(file: String, type: FileType): Flow<Result<String>>
}
