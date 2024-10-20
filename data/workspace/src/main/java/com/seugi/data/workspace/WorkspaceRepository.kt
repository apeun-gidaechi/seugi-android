package com.seugi.data.workspace

import com.seugi.common.model.Result
import com.seugi.data.core.model.ProfileModel
import com.seugi.data.workspace.model.CheckWorkspaceModel
import com.seugi.data.workspace.model.RetrieveMemberModel
import com.seugi.data.workspace.model.WaitWorkspaceModel
import com.seugi.data.workspace.model.WorkspaceModel
import com.seugi.network.workspace.response.RetrieveMemberResponse
import kotlinx.coroutines.flow.Flow

interface WorkspaceRepository {
    suspend fun checkWorkspace(schoolCode: String): Flow<Result<CheckWorkspaceModel>>

    suspend fun workspaceApplication(workspaceId: String, workspaceCode: String, role: String): Flow<Result<String>>

    suspend fun getMembers(workspaceId: String): Flow<Result<List<ProfileModel>>>

    suspend fun getMyWorkspaces(): Flow<Result<List<WorkspaceModel>>>
    suspend fun updateWorkspace(workspaceModel: WorkspaceModel)
    suspend fun insertWorkspace(workspaceModel: WorkspaceModel)

    suspend fun getLocalWorkspaceId(): String
    suspend fun getLocalWorkspace(): WorkspaceModel?
    suspend fun getWaitWorkspaces(): Flow<Result<List<WaitWorkspaceModel>>>
    suspend fun getWorkspaceData(workspaceId: String): Flow<Result<WorkspaceModel>>
    suspend fun createWorkspace(workspaceName: String, workspaceImage: String): Flow<Result<String>>
    suspend fun deleteWorkspace(): Flow<Result<Boolean>>
    suspend fun getWaitMembers(workspaceId: String, role: String) : Flow<Result<List<RetrieveMemberModel>>>
    suspend fun getWorkspaceCode(workspaceId: String): Flow<Result<String>>
    suspend fun addMember(workspaceId: String, userSet: List<Long>, role: String): Flow<Result<Boolean>>
    suspend fun cancelMember(workspaceId: String, userSet: List<Long>, role: String): Flow<Result<Boolean>>
}
