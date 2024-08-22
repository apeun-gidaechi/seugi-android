package com.seugi.data.workspace

import com.seugi.common.model.Result
import com.seugi.data.core.model.ProfileModel
import com.seugi.data.workspace.model.CheckWorkspaceModel
import com.seugi.data.workspace.model.WorkspacePermissionModel
import com.seugi.data.workspace.model.WaitWorkspaceModel
import com.seugi.data.workspace.model.WorkspaceModel
import kotlinx.coroutines.flow.Flow

interface WorkspaceRepository {
    suspend fun checkWorkspace(schoolCode: String): Flow<Result<CheckWorkspaceModel>>

    suspend fun workspaceApplication(workspaceId: String, workspaceCode: String, role: String): Flow<Result<String>>

    suspend fun getMembers(workspaceId: String): Flow<Result<List<ProfileModel>>>

    suspend fun getPermission(workspaceId: String): Flow<Result<WorkspacePermissionModel>>
    suspend fun getMyWorkspaces(): Flow<Result<List<WorkspaceModel>>>
    suspend fun addWorkspaces(workspaces: List<WorkspaceModel>)
    suspend fun getAllWorkspaces(): List<WorkspaceModel>
    suspend fun getWaitWorkspaces(): Flow<Result<List<WaitWorkspaceModel>>>
    suspend fun getWorkspaceData(workspaceId: String): Flow<Result<WorkspaceModel>>
}
