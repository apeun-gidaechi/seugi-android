package com.apeun.gidaechi.data.workspace.repository

import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.common.model.asResult
import com.apeun.gidaechi.common.utiles.DispatcherType
import com.apeun.gidaechi.common.utiles.SeugiDispatcher
import com.apeun.gidaechi.data.core.mapper.toModels
import com.apeun.gidaechi.data.core.model.ProfileModel
import com.apeun.gidaechi.data.workspace.WorkspaceRepository
import com.apeun.gidaechi.data.workspace.mapper.toModel
import com.apeun.gidaechi.data.workspace.model.CheckWorkspaceModel
import com.apeun.gidaechi.network.core.response.safeResponse
import com.apeun.gidaechi.network.workspace.WorkspaceDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WorkspaceRepositoryImpl @Inject constructor(
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
    private val workspaceDatasource: WorkspaceDataSource,
) : WorkspaceRepository {
    override suspend fun checkWorkspace(schoolCode: String): Flow<Result<CheckWorkspaceModel>> {
        return flow {
            val data = workspaceDatasource.checkSchoolCode(schoolCode = schoolCode).safeResponse()
            emit(data.toModel())
        }
            .flowOn(dispatcher)
            .asResult()
    }

    override suspend fun workspaceApplication(workspaceId: String, workspaceCode: String, role: String): Flow<Result<String>> {
        return flow {
            val data = workspaceDatasource.workspaceApplication(
                workspaceId = workspaceId,
                workspaceCode = workspaceCode,
                role = role,
            )
            emit(data.message)
        }
            .flowOn(dispatcher)
            .asResult()
    }

    override suspend fun getMembers(workspaceId: String): Flow<Result<List<ProfileModel>>> = flow {
        val response = workspaceDatasource.getMembers(workspaceId).safeResponse()

        emit(response.toModels())
    }
        .flowOn(dispatcher)
        .asResult()
}
