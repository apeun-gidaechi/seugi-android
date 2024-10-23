package com.seugi.network.catseugi.datasource

import com.seugi.network.catseugi.CatSeugiDataSource
import com.seugi.network.catseugi.request.CatSeugiRequest
import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class CatSeugiDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : CatSeugiDataSource {
    override suspend fun sendText(text: String): BaseResponse<String> = httpClient.get(SeugiUrl.AI) {
        setBody(CatSeugiRequest(
            message = text,
            roomId = "67177e4ac6b844040200d65c"
        ))
        contentType(ContentType.Application.Json)
    }.body()
}
