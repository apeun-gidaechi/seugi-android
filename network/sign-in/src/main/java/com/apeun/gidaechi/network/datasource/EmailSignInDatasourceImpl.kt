package com.apeun.gidaechi.network.datasource

import com.apeun.gidaechi.common.utiles.DispatcherType
import com.apeun.gidaechi.common.utiles.SeugiDispatcher
import com.apeun.gidaechi.network.EmailSignInDatasource
import com.apeun.gidaechi.network.core.SeugiUrl
import com.apeun.gidaechi.network.core.response.BaseResponse
import com.apeun.gidaechi.network.request.EmailSignInRequest
import com.apeun.gidaechi.network.response.EmailSignInResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

class EmailSignInDatasourceImpl @Inject constructor(
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
    private val httpClient: HttpClient,
) : EmailSignInDatasource {
    override suspend fun emailSignIn(body: EmailSignInRequest): BaseResponse<EmailSignInResponse> = httpClient.post("${SeugiUrl.Member.EMAIL_SIGN_IN}") {
        setBody(body = body)
    }.body<BaseResponse<EmailSignInResponse>>()
}
