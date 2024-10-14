package com.seugi.data.member.repository

import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.core.mapper.toModel
import com.seugi.data.core.model.TokenModel
import com.seugi.data.member.MemberRepository
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
    override suspend fun emailSignIn(body: EmailSignInRequest): Flow<Result<TokenModel>> {
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

    // 서버에서 아직 아무런 반환도 없고, 실제로 회원탈퇴가 이루어지는 API가 아니므로, 임시로 이렇게 처리합니다.
    override suspend fun remove(): Flow<Result<Boolean>> = flow {
        kotlinx.coroutines.delay(300)
        emit(true)
    }
        .flowOn(dispatcher)
        .asResult()
}
