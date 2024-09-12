package com.seugi.data.profile.repository

import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.core.mapper.toModel
import com.seugi.data.core.model.ProfileModel
import com.seugi.data.profile.ProfileRepository
import com.seugi.network.core.response.safeResponse
import com.seugi.network.profile.ProfileDataSource
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

    override suspend fun patchProfile(
        workspaceId: String,
        nick: String,
        status: String,
        spot: String,
        belong: String,
        phone: String,
        wire: String,
        location: String,
    ): Flow<Result<Boolean>> = flow {
        val response = dataSource.patchProfile(
            workspaceId = workspaceId,
            nick = nick,
            status = status,
            spot = spot,
            belong = belong,
            phone = phone,
            wire = wire,
            location = location
        ).safeResponse()
        emit(response)
    }
        .flowOn(dispatcher)
        .asResult()
}
