package com.apeun.gidaechi.data.profile

import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.data.profile.model.ProfileModel
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfile(workspaceId: String): Flow<Result<ProfileModel>>
}
