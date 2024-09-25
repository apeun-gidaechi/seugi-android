package com.seugi.file.response

import kotlinx.serialization.Serializable

@Serializable
data class FileResponse(
    val url: String,
    val name: String,
    val byte: Long
)
