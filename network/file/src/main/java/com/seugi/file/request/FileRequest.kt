package com.seugi.file.request

import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.http.ContentType
import io.ktor.http.content.MultiPartData

data class FileRequest(
    val file: ContentType.MultiPart
)