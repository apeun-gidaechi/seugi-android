package com.apeun.gidaechi.data.workspace.repository

import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.common.model.asResult
import com.apeun.gidaechi.common.utiles.DispatcherType
import com.apeun.gidaechi.common.utiles.SeugiDispatcher
import com.apeun.gidaechi.data.core.mapper.toModels
import com.apeun.gidaechi.data.core.model.ProfileModel
import com.apeun.gidaechi.data.workspace.WorkSpaceRepository
import com.apeun.gidaechi.network.core.response.safeResponse
import com.apeun.gidaechi.network.workspace.WorkSpaceDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WorkSpaceRepositoryImpl @Inject constructor(
    private val dataSource: WorkSpaceDataSource,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : WorkSpaceRepository {
    override suspend fun getMembers(workspaceId: String): Flow<Result<List<ProfileModel>>> = flow {
        val response = dataSource.getMembers(workspaceId).safeResponse()

        emit(response.toModels())
    }
        .flowOn(dispatcher)
        .asResult()
}
