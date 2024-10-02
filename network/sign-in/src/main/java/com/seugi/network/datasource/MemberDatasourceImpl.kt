package com.seugi.network.datasource

import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.network.MemberDatasource
import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.Response
import com.seugi.network.core.response.TokenResponse
import com.seugi.network.request.EmailSignInRequest
import com.seugi.network.request.EmailSignUpRequest
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
    override suspend fun emailSignIn(body: EmailSignInRequest): BaseResponse<TokenResponse> = httpClient.post(
        SeugiUrl.Auth.EMAIL_SIGN_IN,
    ) {
        setBody(body = body)
    }.body<BaseResponse<TokenResponse>>()

    override suspend fun getCode(email: String): Response = httpClient.get("${SeugiUrl.Auth.GET_CODE}$email") {
    }.body()

    override suspend fun emailSignUp(name: String, email: String, password: String, code: String): Response = httpClient.post(
        SeugiUrl.Auth.EMAIL_SIGN_UP,
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
