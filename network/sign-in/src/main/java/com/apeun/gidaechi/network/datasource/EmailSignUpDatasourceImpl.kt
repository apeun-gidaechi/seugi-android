package com.apeun.gidaechi.network.datasource

import com.apeun.gidaechi.network.EmailSignUpDatasource
import com.apeun.gidaechi.network.core.SeugiUrl
import com.apeun.gidaechi.network.core.response.Response
import com.apeun.gidaechi.network.request.EmailSignUpReqest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class EmailSignUpDatasourceImpl @Inject constructor(
    private val httpClient: HttpClient
): EmailSignUpDatasource {
    override suspend fun emailSignUp(body: EmailSignUpReqest): Response =
        httpClient.post("${SeugiUrl.Member.EMAIL_SIGN_UP}"){
            setBody(body = body)
        }.body()
}