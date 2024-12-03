package com.seugi.data.workspace.repository

import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.core.mapper.toModels
import com.seugi.data.core.model.ProfileModel
import com.seugi.data.core.model.UserModel
import com.seugi.data.workspace.WorkspaceRepository
import com.seugi.data.workspace.mapper.localToModel
import com.seugi.data.workspace.mapper.toEntity
import com.seugi.data.workspace.mapper.toModel
import com.seugi.data.workspace.mapper.toModels
import com.seugi.data.workspace.model.CheckWorkspaceModel
import com.seugi.data.workspace.model.WaitWorkspaceModel
import com.seugi.data.workspace.model.WorkspaceModel
import com.seugi.local.room.dao.WorkspaceDao
import com.seugi.local.room.dao.WorkspaceNotificationDao
import com.seugi.local.room.entity.WorkspaceNotificationEntity
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
    private val workspaceNotificationDao: WorkspaceNotificationDao
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

    override suspend fun updateWorkspace(workspaceModel: WorkspaceModel) {
        workspaceDao.updateWorkspaceIdById(
            idx = 0,
            newWorkspaceId = workspaceModel.workspaceId,
            workspaceName = workspaceModel.workspaceName,
            workspaceAdmin = workspaceModel.workspaceAdmin,
            workspaceImageUrl = workspaceModel.workspaceImageUrl,
            middleAdmin = workspaceModel.middleAdmin,
            teacher = workspaceModel.teacher,
            student = workspaceModel.student,
        )
        if (workspaceNotificationDao.getWorkspaceByWorkspaceId(workspaceModel.workspaceId) == null) {
            workspaceNotificationDao.insert(WorkspaceNotificationEntity(workspaceModel.workspaceId, true))
        }
    }

    override suspend fun insertWorkspace(workspaceModel: WorkspaceModel) {
        workspaceDao.insert(workspaceModel.toEntity())
        workspaceNotificationDao.insert(WorkspaceNotificationEntity(workspaceModel.workspaceId, true))
    }

    override suspend fun getLocalWorkspaceId(): String {
        return workspaceDao.getWorkspace()?.localToModel()?.workspaceId ?: ""
    }

    override suspend fun getLocalWorkspace(): WorkspaceModel? = workspaceDao.getWorkspace()?.localToModel()

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

    override suspend fun deleteWorkspace(): Flow<Result<Boolean>> = flow {
        workspaceDao.deleteWorkspace()

        emit(true)
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun getWaitMembers(workspaceId: String, role: String): Flow<Result<List<UserModel>>> = flow {
        val response = workspaceDatasource.getWaitMembers(
            workspaceId = workspaceId,
            role = role,
        ).safeResponse()
        emit(response.toModels())
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun getWorkspaceCode(workspaceId: String): Flow<Result<String>> = flow {
        val response = workspaceDatasource.getWorkspaceCode(workspaceId).safeResponse()
        emit(response)
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun addMember(workspaceId: String, userSet: List<Long>, role: String): Flow<Result<Boolean>> = flow {
        val response = workspaceDatasource.addMember(
            workspaceId = workspaceId,
            userSet = userSet,
            role = role,
        ).safeResponse()

        emit(response)
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun cancelMember(workspaceId: String, userSet: List<Long>, role: String): Flow<Result<Boolean>> = flow {
        val response = workspaceDatasource.cancelMember(
            workspaceId = workspaceId,
            userSet = userSet,
            role = role,
        ).safeResponse()

        emit(response)
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun getIsWorkspaceReceiveFCM(workspaceId: String): Flow<Result<Boolean>> = flow {
        val response = workspaceNotificationDao.getWorkspaceByWorkspaceId(workspaceId)

        emit(response?.isReceiveFCM ?: true)
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun changeIsWorkspaceReceiveFCM(isReceiveFCM: Boolean, workspaceId: String): Flow<Result<Boolean>> = flow {
        workspaceNotificationDao.updateIsReceiveFCMByWorkspaceId(
            isReceiveFCM = isReceiveFCM,
            workspaceId = workspaceId
        )
        emit(true)
    }
        .flowOn(dispatcher)
        .asResult()
}
