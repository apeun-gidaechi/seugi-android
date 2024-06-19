package com.apeun.gidaechi.data.profile.repository

import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.common.model.asResult
import com.apeun.gidaechi.common.utiles.DispatcherType
import com.apeun.gidaechi.common.utiles.SeugiDispatcher
import com.apeun.gidaechi.data.core.mapper.toModel
import com.apeun.gidaechi.data.core.model.ProfileModel
import com.apeun.gidaechi.data.profile.ProfileRepository
import com.apeun.gidaechi.network.core.response.safeResponse
import com.apeun.gidaechi.network.profile.ProfileDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProfileRepositoryImpl @Inject constructor(
    private val dataSource: ProfileDataSource,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : ProfileRepository {
    override suspend fun getProfile(workspaceId: String): Flow<Result<ProfileModel>> {
        return flow {
            val response = dataSource.loadUserInfo(workspaceId).safeResponse()
            emit(response.toModel())
        }
            .flowOn(dispatcher)
            .asResult()
    }
}
