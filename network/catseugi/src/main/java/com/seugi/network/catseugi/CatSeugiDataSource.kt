package com.seugi.network.catseugi

import com.seugi.network.core.response.BaseResponse

interface CatSeugiDataSource {

    suspend fun sendText(text: String): BaseResponse<String>
}