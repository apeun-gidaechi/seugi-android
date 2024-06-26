package com.apeun.gidaechi.network.datasource

import com.apeun.gidaechi.common.utiles.DispatcherType
import com.apeun.gidaechi.common.utiles.SeugiDispatcher
import com.apeun.gidaechi.network.MemberDatasource
import com.apeun.gidaechi.network.core.SeugiUrl
import com.apeun.gidaechi.network.core.response.BaseResponse
import com.apeun.gidaechi.network.core.response.Response
import com.apeun.gidaechi.network.request.EmailSignInRequest
import com.apeun.gidaechi.network.request.EmailSignUpRequest
import com.apeun.gidaechi.network.response.EmailSignInResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

class MemberDatasourceImpl @Inject constructor(
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
    private val httpClient: HttpClient,
) : MemberDatasource {
    override suspend fun emailSignIn(body: EmailSignInRequest): BaseResponse<EmailSignInResponse> = httpClient.post("${SeugiUrl.Auth.EMAIL_SIGN_IN}") {
        setBody(body = body)
    }.body<BaseResponse<EmailSignInResponse>>()

    override suspend fun getCode(email: String): Response = httpClient.get("${SeugiUrl.Auth.GET_CODE}$email") {
    }.body()

    override suspend fun emailSignUp(name: String, email: String, password: String, code: String): Response = httpClient.post(
        "${SeugiUrl.Auth.EMAIL_SIGN_UP}",
    ) {
        setBody(
            body = EmailSignUpRequest(
                name = name,
                email = email,
                password = password,
                code = code,
            ),
        )
    }.body()
}
