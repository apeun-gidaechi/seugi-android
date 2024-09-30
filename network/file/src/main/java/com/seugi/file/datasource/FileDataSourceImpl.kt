package com.seugi.file.datasource

import android.util.Log
import com.seugi.file.FileDataSource
import com.seugi.file.response.FileResponse
import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.InputProvider
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.utils.io.streams.asInput
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class FileDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : FileDataSource {
    override suspend fun fileUpload(type: String, file: String): BaseResponse<FileResponse> = httpClient.post(urlString = "${SeugiUrl.File.FILE_UPLOAD}/$type") {
        setBody(
            MultiPartFormDataContent(
                formData {
                    append(
                        "file",
                        File(file).readBytes(),
                        Headers.build {
                            append(HttpHeaders.ContentType, "image/png")
                            append(HttpHeaders.ContentDisposition, "filename=\"${File(file).name}\"")
                        },
                    )
                },
            ),
        )
        onUpload { bytesSentTotal, contentLength ->
            println("Sent $bytesSentTotal bytes from $contentLength")
        }
    }.body()

    override suspend fun fileUpload(type: String, fileName: String, fileMimeType: String, byteArray: ByteArray): BaseResponse<FileResponse> = httpClient.post("${SeugiUrl.File.FILE_UPLOAD}/$type") {
        setBody(
            MultiPartFormDataContent(
                formData {
                    append(
                        "file",
                        byteArray,
                        Headers.build {
                            append(HttpHeaders.ContentType, fileMimeType)
                            append(HttpHeaders.ContentDisposition, "filename=\"${fileName}\"")
                        },
                    )
                },
            ),
        )
        onUpload { bytesSentTotal, contentLength ->
            Log.d("FILE UPLOAD", "Sent $bytesSentTotal bytes from $contentLength")
        }
    }.body()

    override suspend fun fileUpload(type: String, fileName: String, fileMimeType: String, fileStream: InputStream): BaseResponse<FileResponse> = httpClient.submitFormWithBinaryData(
        url = "${SeugiUrl.File.FILE_UPLOAD}/$type",
        formData = formData {
            append(
                key = "file",
                value = InputProvider { fileStream.asInput() },
                headers = Headers.build {
                    append(HttpHeaders.ContentType, fileMimeType) // MIME 타입 설정
                    append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
                },
            )
        },
    ).body()
}
