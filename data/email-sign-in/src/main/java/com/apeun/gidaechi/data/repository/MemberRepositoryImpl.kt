package com.apeun.gidaechi.data.repository

import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.common.model.asResult
import com.apeun.gidaechi.common.utiles.DispatcherType
import com.apeun.gidaechi.common.utiles.SeugiDispatcher
import com.apeun.gidaechi.data.MemberRepository
import com.apeun.gidaechi.data.mapper.toModel
import com.apeun.gidaechi.data.model.EmailSignInModel
import com.apeun.gidaechi.network.MemberDatasource
import com.apeun.gidaechi.network.core.response.Response
import com.apeun.gidaechi.network.core.response.safeResponse
import com.apeun.gidaechi.network.request.EmailSignInRequest
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MemberRepositoryImpl @Inject constructor(
    private val datasource: MemberDatasource,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : MemberRepository {
    override suspend fun emailSignIn(body: EmailSignInRequest): Flow<Result<EmailSignInModel>> {
        return flow {
            val e = datasource.emailSignIn(body).safeResponse()

            emit(e.toModel())
        }
            .flowOn(dispatcher)
            .asResult()
    }

    override suspend fun getCode(email: String): Flow<Result<String>> {
        return flow {
            val data = datasource.getCode(email).message
            emit(data)
        }
            .flowOn(dispatcher)
            .asResult()
    }
}
