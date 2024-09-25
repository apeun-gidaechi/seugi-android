package com.seugi.data.file

import com.seugi.common.model.Result
import com.seugi.data.file.model.FileModel
import com.seugi.data.file.model.FileType
import kotlinx.coroutines.flow.Flow
import java.io.InputStream

interface FileRepository {

    suspend fun fileUpload(file: String, type: FileType): Flow<Result<FileModel>>

    suspend fun fileUpload(type: FileType, fileName: String, fileMimeType: String, fileByteArray: ByteArray): Flow<Result<FileModel>>

    suspend fun fileUpload(type: FileType, fileName: String, fileMimeType: String, fileInputStream: InputStream): Flow<Result<FileModel>>
}
