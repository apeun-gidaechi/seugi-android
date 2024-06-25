package com.apeun.gidaechi.data.workspace

import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.data.core.model.ProfileModel
import com.apeun.gidaechi.data.workspace.model.CheckWorkspaceModel
import kotlinx.coroutines.flow.Flow

interface WorkspaceRepository {
    suspend fun checkWorkspace(schoolCode: String): Flow<Result<CheckWorkspaceModel>>

    suspend fun workspaceApplication(workspaceId: String, workspaceCode: String, role: String): Flow<Result<String>>

    suspend fun getMembers(workspaceId: String): Flow<Result<List<ProfileModel>>>
}