package com.apeun.gidaechi.data.workspace

import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.data.core.model.ProfileModel
import kotlinx.coroutines.flow.Flow

interface WorkSpaceRepository {
    suspend fun getMembers(workspaceId: String): Flow<Result<List<ProfileModel>>>
}
