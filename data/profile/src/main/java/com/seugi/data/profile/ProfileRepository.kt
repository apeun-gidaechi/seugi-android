package com.seugi.data.profile

import com.seugi.common.model.Result
import com.seugi.data.core.model.ProfileModel
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfile(workspaceId: String): Flow<Result<ProfileModel>>
}
