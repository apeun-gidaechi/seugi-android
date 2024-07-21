package com.seugi.data.repository

import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.MemberRepository
import com.seugi.data.mapper.toModel
import com.seugi.data.model.EmailSignInModel
import com.seugi.network.MemberDatasource
import com.seugi.network.core.response.safeResponse
import com.seugi.network.request.EmailSignInRequest
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

    override suspend fun getCode(email: String): Flow<Result<Int>> {
        return flow {
            val data = datasource.getCode(email).status
            emit(data)
        }
            .flowOn(dispatcher)
            .asResult()
    }

    override suspend fun emailSignUp(name: String, email: String, password: String, code: String): Flow<Result<Int>> {
        return flow {
            val data = datasource.emailSignUp(
                name = name,
                email = email,
                password = password,
                code = code,
            )
            emit(data.status)
        }
            .flowOn(dispatcher)
            .asResult()
    }
}
