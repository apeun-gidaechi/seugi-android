package com.seugi.data.workspace

import com.seugi.common.model.Result
import com.seugi.data.core.model.ProfileModel
import com.seugi.data.workspace.model.CheckWorkspaceModel
import com.seugi.data.workspace.model.WorkspacePermissionModel
import kotlinx.coroutines.flow.Flow

interface WorkspaceRepository {
    suspend fun checkWorkspace(schoolCode: String): Flow<Result<CheckWorkspaceModel>>

    suspend fun workspaceApplication(workspaceId: String, workspaceCode: String, role: String): Flow<Result<String>>

    suspend fun getMembers(workspaceId: String): Flow<Result<List<ProfileModel>>>

    suspend fun getPermission(workspaceId: String): Flow<Result<WorkspacePermissionModel>>
}
