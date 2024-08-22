package com.seugi.file.request

import io.ktor.http.ContentType

data class FileRequest(
    val file: ContentType.MultiPart,
)
