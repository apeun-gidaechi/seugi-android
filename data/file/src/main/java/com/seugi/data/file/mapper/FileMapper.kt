package com.seugi.data.file.mapper

import com.seugi.data.file.model.FileModel
import com.seugi.file.response.FileResponse

fun FileResponse.toModel(): FileModel = FileModel(
    fileUrl = fileUrl,
)
