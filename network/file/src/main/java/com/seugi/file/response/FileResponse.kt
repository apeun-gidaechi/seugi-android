package com.seugi.file.response

import kotlinx.serialization.Serializable

@Serializable
data class FileResponse(
    val fileUrl: String,
)
