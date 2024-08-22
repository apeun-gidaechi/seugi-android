package com.seugi.data.file.repository

import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.file.FileRepository
import com.seugi.data.file.mapper.toModel
import com.seugi.data.file.model.FileModel
import com.seugi.file.FileDataSource
import com.seugi.file.request.FileType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
    private val fileDataSource: FileDataSource
): FileRepository {
    override suspend fun fileUpload(file: String, type: FileType): Flow<Result<FileModel>> = flow{
        val response =fileDataSource.fileUpload(file = file, type = type).data

        emit(response.toModel())
    }
        .flowOn(dispatcher)
        .asResult()

}