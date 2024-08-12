package com.seugi.data.workspace.repository

import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.core.mapper.toModels
import com.seugi.data.core.model.ProfileModel
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.data.workspace.mapper.localToModels
import com.seugi.data.workspace.mapper.toEntities
import com.seugi.data.workspace.mapper.toModel
import com.seugi.data.workspace.mapper.toModels
import com.seugi.data.workspace.model.CheckWorkspaceModel
import com.seugi.data.workspace.model.WorkspaceModel
import com.seugi.local.room.dao.WorkspaceDao
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

    override suspend fun getMyWorkspaces(): Flow<Result<List<WorkspaceModel>>> = flow {
        val response = workspaceDatasource.getMyWorkspaces().safeResponse()
        emit(response.toModels())
    }
        .flowOn(dispatcher)
        .asResult()

    // 중복 체크 후 워크스페이스를 데이터베이스에 추가
    override suspend fun addWorkspaces(workspaces: List<WorkspaceModel>) {
        val nonDuplicateWorkspaces = mutableListOf<WorkspaceModel>()

        for (workspace in workspaces) {
            // 데이터베이스에서 동일한 workspaceId 또는 workspaceName을 가진 항목의 개수 조회
            val existingCount = workspaceDao.countExistingWorkspaceByIdOrName(workspace.workspaceId, workspace.workspaceName)
            if (existingCount == 0) {
                // 중복되지 않는 워크스페이스를 리스트에 추가
                nonDuplicateWorkspaces.add(workspace)
            } else {
                // 중복된 데이터에 대한 처리 (예: 로그 출력)
                println("Workspace with id ${workspace.workspaceId} or name ${workspace.workspaceName} already exists.")
            }
        }

        // 중복되지 않는 워크스페이스를 한 번에 데이터베이스에 삽입
        if (nonDuplicateWorkspaces.isNotEmpty()) {
            workspaceDao.insertWorkspaces(nonDuplicateWorkspaces.toEntities())
        }
    }

    override suspend fun getAllWorkspaces(): List<WorkspaceModel?> {
        return workspaceDao.getWorkspace().localToModels()
    }
}
