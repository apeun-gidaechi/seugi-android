package com.seugi.data.workspace.repository

import android.util.Log
import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.core.mapper.toModels
import com.seugi.data.core.model.ProfileModel
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.data.workspace.mapper.localToModel
import com.seugi.data.workspace.mapper.localToModels
import com.seugi.data.workspace.mapper.toEntities
import com.seugi.data.workspace.mapper.toModel
import com.seugi.data.workspace.mapper.toModels
import com.seugi.data.workspace.model.CheckWorkspaceModel
import com.seugi.data.workspace.model.WaitWorkspaceModel
import com.seugi.data.workspace.model.WorkspaceModel
import com.seugi.data.workspace.model.WorkspacePermissionModel
import com.seugi.local.room.dao.WorkspaceDao
import com.seugi.local.room.model.WorkspaceEntity
import com.seugi.network.core.response.safeResponse
import com.seugi.network.workspace.WorkspaceDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WorkspaceRepositoryImpl @Inject constructor(
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
    private val workspaceDatasource: WorkspaceDataSource,
    private val workspaceDao: WorkspaceDao,
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

    override suspend fun getPermission(workspaceId: String): Flow<Result<WorkspacePermissionModel>> = flow {
        val response = workspaceDatasource.getPermission(workspaceId).safeResponse()

        emit(response.toModel())
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun getMyWorkspaces(): Flow<Result<List<WorkspaceModel>>> = flow {
        val response = workspaceDatasource.getMyWorkspaces().safeResponse()
        emit(response.toModels())
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun addWorkspaceId(workspacesId: String) {
        Log.d("TAG", "id: $workspacesId ")
        workspaceDao.updateWorkspaceIdById(0, workspacesId)
    }

    override suspend fun getWorkspaceId(): String {
        return workspaceDao.getWorkspaceId().localToModel().workspaceId
    }

    override suspend fun getWaitWorkspaces(): Flow<Result<List<WaitWorkspaceModel>>> = flow {
        val response = workspaceDatasource.getWaitWorkspace().safeResponse()
        emit(response.toModels())
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun getWorkspaceData(workspaceId: String): Flow<Result<WorkspaceModel>> = flow {
        val response = workspaceDatasource.getWorkspaceData(workspaceId).safeResponse()
        emit(response.toModel())
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun createWorkspace(workspaceName: String, workspaceImage: String): Flow<Result<String>> = flow {
        val response = workspaceDatasource.createWorkspace(
            workspaceName = workspaceName,
            workspaceImage = workspaceImage,
        ).safeResponse()

        emit(response)
    }
        .flowOn(dispatcher)
        .asResult()
}
